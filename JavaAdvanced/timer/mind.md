
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