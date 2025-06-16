package org.pt.io;

import java.io.*;

/**
 * @ClassName TraditionalIOFileCopier
 * @Author pt
 * @Description
 * @Date 2025/6/13 20:12
 **/
public class TraditionalIOFileCopier {
    /**
     * 使用传统的带缓冲的流来复制文件。
     *
     * @param source 源文件路径
     * @param dest   目标文件路径
     * @throws IOException IO异常
     */
    protected void copyFile(File source, File dest) throws IOException {
        // 使用try-with-resources确保流能被自动关闭
        try (InputStream in = new BufferedInputStream(new FileInputStream(source));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(dest))) {
            byte[] buffer = new byte[8192]; // 8KB的缓冲区
            int bytesRead;
            // 循环从输入流读取数据到缓冲区，再从缓冲区写入到输出流
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
