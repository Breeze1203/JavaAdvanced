package org.pt.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName NIOOutHeapBufferFileCopier
 * @Author pt
 * @Description
 * @Date 2025/6/13 20:23
 **/
public class NIOOutHeapBufferFileCopier {
    /**
     * 使用NIO的Channel和基于JVM堆外的HeapBuffer来复制文件。
     *
     * @param source 源文件路径
     * @param dest   目标文件路径
     * @throws IOException IO异常
     */
    public void copyFile(File source, File dest) throws IOException {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
             FileChannel destChannel = new FileOutputStream(dest).getChannel()) {
            // 创建一个基于本地内存的缓冲区
            ByteBuffer buffer =  ByteBuffer.allocateDirect(8192);; // 8KB的堆缓冲区
            while (sourceChannel.read(buffer) != -1) {
                // 切换缓冲区为读模式
                buffer.flip();
                // 确保缓冲区的数据全部写入目标通道
                while (buffer.hasRemaining()) {
                    destChannel.write(buffer);
                }
                // 清空缓冲区，为下一次读取做准备
                buffer.clear();
            }
        }
    }
}
