package org.pt.finalize;

public class Book {
    boolean check=false;
    Book(boolean check){
        this.check=check;
    }

    void checkIn(){
        check=false;
    }
    protected void finalize(){
        if (check){
            System.out.println("Error checked out");
        }
    }

    public static void main(String[] args) {
        Book book = new Book(true);
        book.checkIn();
        new Book(true);
        System.gc();
    }
}
