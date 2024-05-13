package org.pt.clone;

public class Writer implements Cloneable{
    private int age;
    private String name;

    private Book book;

    public Writer(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", book=" + book +
                '}';
    }

    /*
    浅拷贝

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

     */

    /*
    深拷贝
    clone() 方法当中，不再只调用 Object 的 clone() 方法对 Writer 进行克隆了，还对 Book 也进行了克隆
     */
    protected Object clone() throws CloneNotSupportedException {
        Writer writer = (Writer) super.clone();
        writer.setBook((Book) writer.getBook().clone());
        return writer;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
