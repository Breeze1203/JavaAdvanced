package org.pt.io;

import java.io.File;
import java.io.IOException;

public class FileExample {
    public static void main(String[] args) throws IOException {
        File file = new File("test.txt");
        if(file.createNewFile()){
            System.out.println("文件创建成功");
        }else {
            System.out.println("文件已存在");
        }
        if(file.delete()){
            System.out.println("文件删除成功");
        }else {
            System.out.println("文件删除失败");
        }
        // 文件重命名
        File oldFile = new File("old.txt");
        boolean create = oldFile.createNewFile();
        System.out.println(create);
        File newFile = new File("new.txt");
        if(oldFile.renameTo(newFile)){
            System.out.println("文件重命名成功");
        }else {
            System.out.println("文件重命名失败");
        }

    }
}
