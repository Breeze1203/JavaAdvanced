# Timer 执行逻辑详解

## 整体架构分层（从上到下）

层级           | 职责                               | 主要类/组件                     | 线程模型
---------------|------------------------------------|----------------------------------|---------------------
**API 层**     | 对外接口、参数校验、状态暴露       | TimerSchedulerFacade            | 调用者线程
**调度层**     | 维护到期时间小顶堆、等待/唤醒      | HeapTaskScheduler               | 单一调度线程（daemon）
**执行层**     | 并发执行任务、超时控制、重试触发   | TaskExecutor                    | 线程池（固定大小）
**状态层**     | 任务生命周期状态管理               | ConcurrentHashMap<Long, TaskStatus> | 多线程安全
**持久化层**   | 任务快照存储与恢复                 | TaskPersistence 接口实现         | 调用时同步/异步均可

## 主要流程图（文字版）


```mermaid
  root((timer))
    API 层
      TimerSchedulerFacade
        职责: 对外API、参数校验、状态查询
        方法
          scheduleAtFixedRate(...)
            创建 TimerTask
            保存到持久化 (save)
            初始状态: RUNNABLE
            调用 scheduler.scheduleInternal
          cancel(id)
            从 scheduler 移除
            状态: CANCELLED
            删除持久化
          queryStatus(id)
            从 statuses Map 获取
          shutdown()
            关闭 scheduler 和 executor
        启动时: loadPendingTasks()
          从 persistence.loadAll()
          过滤未到期任务 → scheduleInternal
    调度层
      HeapTaskScheduler
        职责: 小顶堆管理、到期调度、等待/唤醒
        数据结构
          PriorityQueue<TimerTask> (小顶堆，按 nextExecuteTimeMs 排序)
          ConcurrentHashMap<Long, TimerTask> (taskMap, 快速查找/取消)
        线程: 单一 daemon 线程 (scheduleLoop)
        核心循环: scheduleLoop()
          while running
            加锁计算 nowMs (带漂移校正)
            while 队列空 or 堆顶未到期
              wait(剩余 ms)
            poll() 取出堆顶任务
            如果取消 → 丢弃
            状态 → RUNNING
            executor.executeTask(task, onComplete 回调)
        回调: onTaskComplete(task, success)
          加锁
            if !success and retry < maxRetries
              retryCount++
              reschedule(true) 指数退避
              offer 到堆
              状态 → RUNNABLE
              persistence.update
            else
              if 周期 && success && 未取消
                retryCount = 0
                reschedule(false) fixed-rate/delay
                offer 到堆
                状态 → RUNNABLE
                persistence.update
              else
                状态 → COMPLETED / FAILED
                从 map/queue 移除
                persistence.delete
            notify()
        时间校正: currentTimeMillisWithDriftCorrection()
          用 nanoTime + milliBase 补偿时钟跳变
        方法
          scheduleInternal(task): offer + put map + notify
          remove(id): cancel + remove queue/map
          shutdown(): set false + notify
    执行层
      TaskExecutor
        职责: 并发执行、超时保护、重试触发
        线程池: FixedThreadPool (N 线程)
        方法: executeTask(task, callback)
          pool.submit(...)
            future = pool.submit(command)
            future.get(timeout)  // 超时抛 TimeoutException
            success = true / false (catch 异常/超时)
            finally: callback.onComplete(task, success)
        shutdown(): pool.shutdown
    状态层
      ConcurrentHashMap<Long, TaskStatus>
        职责: 线程安全状态追踪
        枚举: RUNNABLE, RUNNING, COMPLETED, FAILED, CANCELLED
        使用: facade.queryStatus, executor/scheduler 更新
    持久化层
      TaskPersistence 接口
        职责: 任务序列化/恢复 (Gson JSON)
        方法
          save(task)
          update(task)
          delete(id)
          loadAll() → List<TimerTask>
        实现示例
          MemoryPersistence (Map 模拟 Redis)
          DbPersistence (H2 DB, SQL 操作)
          RedisPersistence (Jedis, JSON 存储)
        注意: transient 字段反序列化后重建 (e.g., AtomicInteger)
    任务实体
      TimerTask
        字段
          id (AtomicLong 生成)
          name
          command (Runnable)
          nextExecuteTimeMs
          periodMs
          fixedDelay (boolean)
          maxRetries
          retryBackoffMs
          retryCount (AtomicInteger)
          cancelled (AtomicBoolean)
        方法
          compareTo: 按 nextExecuteTimeMs 排序
          reschedule(afterFailure): 计算下次时间 (指数退避 / fixed / rate)
          isCancelled / cancel
    整体执行流程
      用户提交 → API 创建/持久化 → 调度入堆 → 调度线程 wait/poll → 执行线程 run/timeout → 回调重试/重调度/完成
```

## 关键决策点与行为

| 场景                     | 行为描述                                                                 | 涉及状态变化                  | 是否重新入堆 |
|--------------------------|--------------------------------------------------------------------------|-------------------------------|--------------|
| 任务到期，正常执行成功   | 执行 command → success=true → reschedule（如果周期任务）                | RUNNING → RUNNABLE / COMPLETED | 是（周期）   |
| 任务超时（>10s）         | TimeoutException → success=false → 进入重试逻辑                         | RUNNING → RUNNABLE（重试中）   | 是（重试）   |
| 任务抛异常               | 同超时，进入重试逻辑                                                     | 同上                           | 是（重试）   |
| 重试次数达到 maxRetries  | success=false 且 retryCount 已满 → 标记 FAILED，不再入堆                | RUNNING → FAILED               | 否           |
| 用户调用 cancel(id)      | 从 queue + taskMap 移除，标记 CANCELLED，删除持久化                     | RUNNABLE/RUNNING → CANCELLED   | 否           |
| fixed-rate vs fixed-delay| fixed-rate：next += period（可能累积漂移）<br>fixed-delay：next = now + period | —                              | —            |
| 系统时间被调整           | 使用 nanoTime + milliBase 校正，抵抗跳变（±5s 阈值触发重置基准）        | —                              | —            |

## 线程与锁使用总结

- **调度线程**：唯一一个，负责 wait/notify 和 poll/offer，只持有短时锁
- **执行线程池**：N 个线程，执行真实业务逻辑 + 超时 get()
- **锁粒度**：只保护 PriorityQueue + taskMap 的读写，执行和回调时已释放锁
- **状态 map**：ConcurrentHashMap，无锁读写
- **持久化**：目前同步调用（save/update/delete），生产可改为异步

## 常见问题与当前设计权衡

问题                           | 当前处理方式                          | 潜在改进方向
-------------------------------|---------------------------------------|-----------------------------
取消正在运行的任务             | 只取消下次调度，不中断当前执行        | 可加 interrupt 或 cancel token
PriorityQueue remove 是 O(n)   | 接受（任务量少时无所谓）              | 换 TreeSet 或加 HashMap 索引
持久化开销大                   | 每次变更都写（save/update/delete）    | 批量写、延迟写、只存元数据
单点调度线程瓶颈               | 目前单线程调度（类似 Quartz）         | 可升级为多调度线程 + 分片
任务执行顺序严格保证           | 同一个任务的多次执行不会并发          | 如果要允许并发同任务，需改设计
