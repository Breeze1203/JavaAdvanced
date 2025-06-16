package org.pt.string;

/**
 * @ClassName StringInternMemoryTest
 * @Author pt
 * @Description
 * @Date 2025/6/14 21:24
 **/
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * 本测试类旨在通过对比实验，展示 String.intern() 方法在节省内存空间方面的作用。
 * <p>
 * 测试原理：
 * 1.  `testStringCreationWithoutIntern`: 创建大量内容重复的字符串对象，但不使用 intern()。
 * 这些对象虽然内容相同，但在堆内存中是独立的对象，会占用大量内存。
 * 2.  `testStringCreationWithIntern`: 创建同样数量和内容的字符串对象，但对每个对象调用 intern()。
 * 当调用 intern() 时，JVM 会检查字符串常量池中是否已存在内容相同的字符串。
 * 如果存在，则返回常量池中的引用；如果不存在，则将该字符串添加到常量池并返回其引用。
 * 这使得所有内容相同的字符串都指向同一个内存地址，从而大大减少内存消耗。
 * <p>
 * 通过对比两个测试前后所占用的内存差异，可以直观地看出 intern() 的内存优化效果。
 */
public class StringInternMemoryTest {

    // 定义测试中使用的常量
    private static final int LIST_SIZE = 200_000; // 创建的字符串数量
    private static final String BASE_STRING_A = "TestStringA";
    private static final String BASE_STRING_B = "TestStringB";
    private static final String BASE_STRING_C = "ThisIsAVeryLongStringForTestingMemoryUsageAndInterningEffectiveness";
    private static final String BASE_STRING_D = "AnotherLongerStringJustToMakeSureTheMemoryFootprintIsNoticeable";


    /**
     * 测试方法一：不使用 intern() 方法创建字符串
     * 在这个方法中，我们会创建大量重复的字符串对象。每一个 new String(...) 都会在堆上创建一个新的对象。
     */
    @Test
    public void testStringCreationWithoutIntern() {
        System.out.println("--- 开始测试：不使用 String.intern() ---");
        List<String> stringList = new ArrayList<>(LIST_SIZE);
        // 在执行前进行一次垃圾回收，并记录初始内存使用情况
        long memoryBefore = getUsedMemory();
        System.out.println("初始内存使用: " + formatMemory(memoryBefore));
        // 循环创建大量内容重复的字符串对象
        for (int i = 0; i < LIST_SIZE; i++) {
            // 通过 new String(...) 强制在堆上创建新对象 即使内容相同，它们也是不同的对象实例
            String str;
            switch (i % 4) {
                case 0:
                    str = new String(BASE_STRING_A);
                    break;
                case 1:
                    str = new String(BASE_STRING_B);
                    break;
                case 2:
                    str = new String(BASE_STRING_C);
                    break;
                default:
                    str = new String(BASE_STRING_D);
                    break;
            }
            stringList.add(str);
        }

        // 记录执行后的内存使用情况
        long memoryAfter = getUsedMemory();
        System.out.println("创建 " + LIST_SIZE + " 个字符串后的内存使用: " + formatMemory(memoryAfter));
        long memoryUsed = memoryAfter - memoryBefore;
        System.out.println(">>>>>【不使用 intern】内存增量: " + formatMemory(memoryUsed) + " <<<<<");
        System.out.println("------------------------------------------\n");
        // 防止 JIT 编译器优化掉 list
        System.out.println("列表中第一个元素: " + stringList.get(0));
    }


    /**
     * 测试方法二：使用 intern() 方法来处理字符串
     * 在这个方法中，我们对每个新创建的字符串调用 .intern() 方法。
     * 这会使得所有内容相同的字符串共享同一个内存地址。
     */
    @Test
    public void testStringCreationWithIntern() {
        System.out.println("--- 开始测试：使用 String.intern() ---");
        List<String> stringList = new ArrayList<>(LIST_SIZE);

        // 在执行前进行一次垃圾回收，并记录初始内存使用情况
        long memoryBefore = getUsedMemory();
        System.out.println("初始内存使用: " + formatMemory(memoryBefore));

        // 循环创建字符串并调用 intern() 方法
        for (int i = 0; i < LIST_SIZE; i++) {
            // 同样创建新对象，但立即调用 intern()
            // intern() 会返回字符串常量池中的引用
            String str;
            switch (i % 4) {
                case 0:
                    str = new String(BASE_STRING_A).intern();
                    break;
                case 1:
                    str = new String(BASE_STRING_B).intern();
                    break;
                case 2:
                    str = new String(BASE_STRING_C).intern();
                    break;
                default:
                    str = new String(BASE_STRING_D).intern();
                    break;
            }
            stringList.add(str);
        }

        // 记录执行后的内存使用情况
        long memoryAfter = getUsedMemory();
        System.out.println("创建 " + LIST_SIZE + " 个字符串并 intern 后的内存使用: " + formatMemory(memoryAfter));
        long memoryUsed = memoryAfter - memoryBefore;
        System.out.println(">>>>>【使用 intern】内存增量: " + formatMemory(memoryUsed) + " <<<<<");
        System.out.println("----------------------------------------\n");

        // 防止 JIT 编译器优化掉 list
        System.out.println("列表中第一个元素: " + stringList.get(0));
    }


    /**
     * 获取当前JVM已使用的内存。
     * 通过总内存减去空闲内存来计算，并在计算前建议进行GC。
     * @return 已使用的内存，单位为字节 (bytes)。
     */
    private long getUsedMemory() {
        // 建议JVM执行垃圾回收，以获得更准确的内存使用情况
        // 这只是一个建议，JVM不保证立即执行GC
        System.gc();
        try {
            // 等待一段时间，让GC有机会执行
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }


    /**
     * 将字节大小格式化为更易读的 KB 或 MB。
     * @param bytes 字节数
     * @return 格式化后的字符串
     */
    private String formatMemory(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        }
        long kilobytes = bytes / 1024;
        if (kilobytes < 1024) {
            return kilobytes + " KB";
        }
        return kilobytes / 1024 + " MB";
    }
}
