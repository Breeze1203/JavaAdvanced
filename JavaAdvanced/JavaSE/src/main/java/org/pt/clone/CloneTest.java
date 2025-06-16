package org.pt.clone;

public class CloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
//        Writer writer1 = new Writer(22, "彭涛");
//        writer1.setBook(new Book("肖申克的救赎",30L));
//        Writer writer2 = (Writer) writer1.clone();
//        System.out.println("writer1"+writer1);
//        System.out.println("writer2"+writer2);
//        Book book = writer2.getBook();
//        book.setName("草泥马");
//        System.out.println("--------修改后-------");
//        System.out.println("writer1"+writer1);
//        System.out.println("writer2"+writer2);
        Book book = new Book("张三",30L);
        System.out.println(book.toString());
        System.out.println(book.hashCode());
        book.change("莉丝",60L);
        System.out.println(book.toString());
        System.out.println(book.hashCode());
    }
}
