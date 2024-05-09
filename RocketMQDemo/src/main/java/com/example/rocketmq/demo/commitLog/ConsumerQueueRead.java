package com.example.rocketmq.demo.commitLog;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

public class ConsumerQueueRead {
    public static void main(String[] args)throws Exception {
        String path1 = "/Users/hadoop/store/consumequeue/TransactionTopic/1/00000000000000000000";
        readConsumerQueue(path1);


    }

    public static  void readConsumerQueue(String path) throws Exception {
        //生成ByteBuffer
            ByteBuffer buffer = read(path);
        while (true){

            long offset = buffer.getLong();
            long size = buffer.getInt();
            long code = buffer.getLong();
            if (size==0){
                break;
            }
            System.out.println("消息偏移量:" +offset+",消息长度:"+size+",TagHashCode:"+code);
        }
        System.out.println("--------------------------");
    }

    public static ByteBuffer read(String path)throws Exception{
        File file = new File(path);
        FileInputStream fin = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fin.read(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer;
    }

}
