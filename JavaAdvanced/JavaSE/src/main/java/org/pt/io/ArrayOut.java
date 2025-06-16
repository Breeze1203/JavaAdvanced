package org.pt.io;


import java.io.ByteArrayOutputStream;

public class ArrayOut {
    public static void main(String[] args) {
        ByteArrayOutputStream in = new ByteArrayOutputStream();
        byte[] bytes = "沉默彭涛".getBytes();
        in.write(bytes,0,bytes.length);
        String string = in.toString();
        System.out.println(string);
    }
}
