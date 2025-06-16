package org.pt.io;

/**
 * @ClassName IOvsNIOTest
 * @Author pt
 * @Description
 * @Date 2025/6/13 20:20
 **/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class IOvsNIOTest {

    private static final String FILE_NAME_PREFIX = "test_file_";
    private static final int FILE_SIZE_MB = 500; // 定义测试文件大小（MB）

    public static void main(String[] args) {
        //  准备测试文件
        File sourceFile = null;
        File traditionalDestFile = new File(FILE_NAME_PREFIX + "traditional_copy.tmp");
        File nioDestFile = new File(FILE_NAME_PREFIX + "nio_heap_copy.tmp");
        File nioDirectDestFile = new File("copy_nio_direct.tmp");
        try {
            System.out.println("正在创建大小为 " + FILE_SIZE_MB + "MB 的测试文件，请稍候...");
            sourceFile = createLargeTestFile(FILE_NAME_PREFIX + "source.tmp", FILE_SIZE_MB);
            System.out.println("测试文件创建完成: " + sourceFile.getAbsolutePath());
            System.out.println("-------------------------------------------------");
            // 测试传统 I/O
            TraditionalIOFileCopier traditionalCopier = new TraditionalIOFileCopier();
            long startTimeTraditional = System.nanoTime();
            traditionalCopier.copyFile(sourceFile, traditionalDestFile);
            long endTimeTraditional = System.nanoTime();
            long traditionalDuration = (endTimeTraditional - startTimeTraditional) / 1_000_000;
            System.out.printf("传统 I/O (Stream) 复制耗时:            %d ms%n", traditionalDuration);
            // 测试 NIO (Heap Buffer)
            NIOHeapBufferFileCopier nioHeapCopier = new NIOHeapBufferFileCopier();
            long startTimeNIOHeap = System.nanoTime();
            nioHeapCopier.copyFile(sourceFile, nioDestFile);
            long endTimeNIOHeap = System.nanoTime();
            long nioHeapDuration = (endTimeNIOHeap - startTimeNIOHeap) / 1_000_000;
            System.out.printf("NIO (Heap Buffer) 复制耗时:          %d ms%n", nioHeapDuration);

            // 测试 NIO (Direct Buffer / 本地内存)
            NIOOutHeapBufferFileCopier nioDirectCopier = new NIOOutHeapBufferFileCopier();
            long startTimeNIODirect = System.nanoTime();
            nioDirectCopier.copyFile(sourceFile, nioDirectDestFile);
            long endTimeNIODirect = System.nanoTime();
            long nioDirectDuration = (endTimeNIODirect - startTimeNIODirect) / 1_000_000;
            System.out.printf("NIO (Direct Buffer) 复制耗时:        %d ms%n", nioDirectDuration);
            // 性能对比
            System.out.println("-------------------------------------------------");
            // 对比传统IO vs NIO(Heap)
            if (nioHeapDuration > 0 && traditionalDuration > nioHeapDuration) {
                double improvement = ((double) (traditionalDuration - nioHeapDuration) / traditionalDuration) * 100;
                System.out.printf("-> NIO (Heap) 比 传统I/O 快了约: %.2f%%%n", improvement);
            }

            // 对比NIO(Heap) vs NIO(Direct)
            if (nioDirectDuration > 0 && nioHeapDuration > nioDirectDuration) {
                double improvement = ((double) (nioHeapDuration - nioDirectDuration) / nioHeapDuration) * 100;
                System.out.printf("-> NIO (Direct) 比 NIO (Heap) 快了约: %.2f%%%n", improvement);
            }

            // 对比传统IO vs NIO(Direct)
            if (nioDirectDuration > 0 && traditionalDuration > nioDirectDuration) {
                double improvement = ((double) (traditionalDuration - nioDirectDuration) / traditionalDuration) * 100;
                System.out.printf("-> NIO (Direct) 比 传统I/O 快了约: %.2f%%%n", improvement);
            }

        } catch (IOException e) {
            System.err.println("测试过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 5. 清理测试文件
            System.out.println("-------------------------------------------------");
            System.out.println("正在清理测试文件...");
            cleanup(sourceFile, traditionalDestFile, nioDestFile);
            System.out.println("清理完成。");
        }
    }

    // 创建文件
    private static File createLargeTestFile(String fileName, int sizeInMB) throws IOException {
        File file = new File(fileName);
        file.deleteOnExit();
        byte[] junk = new byte[1024 * 1024];
        new Random().nextBytes(junk);

        try (FileOutputStream out = new FileOutputStream(file)) {
            for (int i = 0; i < sizeInMB; i++) {
                out.write(junk);
            }
        }
        return file;
    }

    private static void cleanup(File... files) {
        for (File file : files) {
            if (file != null && file.exists()) {
                if (!file.delete()) {
                    System.err.println("警告: 未能删除文件 " + file.getAbsolutePath());
                }
            }
        }
    }
}