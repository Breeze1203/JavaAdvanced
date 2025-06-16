package org.pt.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOut {
    public static void main(String[] args) {
        try {
            FileOutputStream out = new FileOutputStream("out.txt");
            out.write("彭涛".getBytes());
            out.write("男".getBytes());
            out.write("22岁".getBytes());
            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
