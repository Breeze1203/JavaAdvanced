package com.example.springcachedemo;

/**
 * @ClassName SimpleBookRepository
 * @Author pt
 * @Description
 * @Date 2025/1/21 15:13
 **/
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SimpleBookRepository implements BookRepository {

    @Cacheable(value = "books")
    @Override
    public Book getByIsbn(String isbn) {
        simulateSlowService();
        System.out.println("查询数据库");
        return new Book(isbn, "Some book");
    }

    // Don't do this at home
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}