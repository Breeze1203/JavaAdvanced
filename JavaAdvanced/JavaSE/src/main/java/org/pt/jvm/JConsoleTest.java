package org.pt.jvm;

/**
 * @ClassName JConsoleTest
 * @Author pt
 * @Description
 * @Date 2025/5/26 22:36
 **/
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JConsoleTest {

    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("JConsole 测试程序已启动。");
        System.out.println("请保持此程序运行，然后打开 JConsole 并连接到此进程。");
        System.out.println("进程名称通常会包含 'JConsoleTest'。");

        List<byte[]> memoryHog = new ArrayList<>();
        long iteration = 0;

        try {
            while (true) {
                iteration++;
                System.out.println("\n--- 第 " + iteration + " 轮迭代开始 ---");
                // 1. 模拟内存分配 (方便观察堆内存变化和GC活动)
                // 每轮增加大约 1MB 的内存占用
                int objectsToCreate = 1024; // 创建 1024 个 1KB 的对象
                System.out.println("正在分配 " + objectsToCreate + " 个 1KB 大小的对象 (总计约 " + (objectsToCreate / 1024.0) + " MB)...");
                for (int i = 0; i < objectsToCreate; i++) {
                    memoryHog.add(new byte[1024]); // 每个对象 1KB
                }
                System.out.println("当前 memoryHog 列表中的对象数量: " + memoryHog.size());
                System.out.println("预估 memoryHog 占用内存: " + (memoryHog.size() / 1024.0) + " MB");

                // 为了避免程序因为内存耗尽而过快退出，可以设置一个上限
                // 如果列表过大，可以考虑清空一部分，但对于观察GC，让它增长直到OOM也是一种测试场景
                if (memoryHog.size() > 200 * 1024) { // 大约 200MB 时
                    System.out.println("内存占用较大，清空一半对象以模拟更长时间运行...");
                    int halfSize = memoryHog.size() / 2;
                    memoryHog.subList(0, halfSize).clear();
                    System.out.println("清理后 memoryHog 列表中的对象数量: " + memoryHog.size());
                }


                // 2. 模拟一些CPU活动 (方便观察CPU使用率)
                System.out.println("正在进行一些轻量级CPU计算...");
                long cpuWorkStart = System.nanoTime();
                for (int i = 0; i < 20_000_000; i++) {
                    Math.sin(random.nextDouble()); // 一些数学运算
                }
                long cpuWorkEnd = System.nanoTime();
                System.out.println("CPU计算耗时: " + (cpuWorkEnd - cpuWorkStart) / 1_000_000 + " ms");

                // 3. 启动一个短暂的线程 (方便观察线程变化)
                if (iteration % 5 == 0) { // 每5轮启动一个
                    System.out.println("正在启动一个临时工作线程...");
                    Thread workerThread = new Thread(() -> {
                        try {
                            System.out.println("临时工作线程 [" + Thread.currentThread().getName() + "] 开始工作...");
                            Thread.sleep(2000); // 工作2秒
                            System.out.println("临时工作线程 [" + Thread.currentThread().getName() + "] 完成工作。");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }, "MyWorkerThread-" + iteration);
                    workerThread.start();
                }


                // 4. 主线程休眠一段时间，让JConsole有足够的时间刷新，也避免CPU持续100%
                System.out.println("主线程休眠5秒钟...");
                Thread.sleep(5000); // 休眠5秒

            }
        } catch (InterruptedException e) {
            System.out.println("程序被中断，即将退出。");
            Thread.currentThread().interrupt(); // 保持中断状态
        } catch (OutOfMemoryError oom) {
            System.err.println("!!!!!!!!!! 内存溢出 (OutOfMemoryError) !!!!!!!!!");
            System.err.println("这是一个观察JVM在内存压力下行为的好时机。");
            System.err.println("程序即将因内存不足而终止。");
            // JConsole如果已连接，仍能展示发生OOM时的状态
        } finally {
            System.out.println("\nJConsole 测试程序即将结束。");
        }
    }
}