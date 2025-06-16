package org.pt.gc;

/**
 * @ClassName GcBehaviorDemo
 * @Author pt
 * @Description
 * @Date 2025/6/14 21:36
 **/
import java.util.ArrayList;
import java.util.List;

public class GcBehaviorDemo {

    // 定义每个对象的大小 (1 MB)
    private static final int OBJECT_SIZE = 400 * 1024;

    // 用于持有对象的引用，防止被立即回收
    private static final List<byte[]> objectHolder = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        printMemoryUsage("程序启动");
        // --- 阶段1 在Eden区快速分配小对象，触发Minor GC ---
        System.out.println("\n===== 阶段1: 快速分配小对象，填满Eden区，触发Minor GC =====");
        for (int i = 0; i < 15; i++) {
            allocateAndPrint(i);
            Thread.sleep(2000); // 短暂休眠，让GC日志更容易观察
        }
        printMemoryUsage("阶段1结束");

        // --- 行为2: 让部分对象存活下来，观察其晋升到老年代 ---
        // 在阶段1中，部分对象（被objectHolder持有）已经在多次Minor GC中存活下来，
        // 应该已经被晋升到老年代了。我们再分配一些，确保老年代有数据。
        System.out.println("\n===== 阶段2: 持续分配，观察对象晋升至老年代 =====");
        for (int i = 0; i < 10; i++) {
            allocateAndPrint(15 + i);
            Thread.sleep(200);
        }
        printMemoryUsage("阶段2结束");


        // --- 行为3: 分配一个大对象，可能直接进入老年代 ---
        System.out.println("\n===== 阶段3: 分配一个大对象 =====");
        System.out.println("准备分配一个 8 MB 的大对象...");
        // G1中，超过Region大小一半的对象被视为大对象（Humongous Object）
        // 会被直接分配到专门的Humongous Region，逻辑上属于老年代
        byte[] largeObject = new byte[8 * 1024 * 1024];
        System.out.println("大对象分配完毕。");
        printMemoryUsage("阶段3结束");

        // --- 行为4: 手动触发Full GC ---
        System.out.println("\n===== 阶段4: 手动触发 Full GC =====");
        System.out.println("清空所有引用...");
        objectHolder.clear();
        largeObject = null; // 清除大对象引用
        System.out.println("调用 System.gc() 建议执行 Full GC...");
        System.gc();
        Thread.sleep(2000); // 等待GC完成
        printMemoryUsage("Full GC后");

        System.out.println("\n程序结束。请分析控制台输出的GC日志。");
    }

    /**
     * 分配一个1MB的对象，并将其加入到持有列表中
     */
    private static void allocateAndPrint(int count) {
        System.out.printf("分配第 %d 个对象 (1 MB)...%n", count + 1);
        objectHolder.add(new byte[OBJECT_SIZE]);
    }

    /**
     * 打印当前堆内存的使用情况
     */
    private static void printMemoryUsage(String stage) {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        System.out.printf("[%s] - 堆内存: 已用 %.2f MB, 空闲 %.2f MB, 总共 %.2f MB%n",
                stage,
                bytesToMb(usedMemory),
                bytesToMb(freeMemory),
                bytesToMb(totalMemory));
    }

    private static double bytesToMb(long bytes) {
        return (double) bytes / 1024 / 1024;
    }
}
