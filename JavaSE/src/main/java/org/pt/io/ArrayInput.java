package org.pt.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ArrayInput {
    public static void main(String[] args) throws IOException {
        BufferedInputStream is = new BufferedInputStream(new ByteArrayInputStream("沉默彭涛".getBytes("utf-8")));
        // 定义一个字节数组用于存储读取到的数据
        byte[] flush = new byte[1024];
        // 定义一个变量用于存储每次读取到的字节数
        int len = 0;
        // 循环读取字节数组中的数据，并输出到控制台
        while (-1 != (len = is.read(flush))) {
            // 将读取到的字节转换为对应的字符串，并输出到控制台
            System.out.println(new String(flush, 0, len));
        }
        // 关闭输入流，释放资源
        is.close();
    }
}
