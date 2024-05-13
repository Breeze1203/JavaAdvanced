package org.pt.io;

import java.io.FileInputStream;
import java.io.IOException;

public class FileInput {
    public static void main(String[] args) throws IOException {
        int b;
        FileInputStream in = new FileInputStream("out.txt");
        while ((b=in.read())!=-1){
            System.out.println(b);
        }
    }
}
