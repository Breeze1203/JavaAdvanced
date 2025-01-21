package com.example.springcachedemo;

public interface BookRepository {
    Book getByIsbn(String isbn);
}
