package org.pengtao.backendabstraction.service.base;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseServiceImpl<T, ID> implements IBaseService<T, ID> {

    // 使用 final, 通过构造函数注入
    private final JpaRepository<T, ID> repository;

    public BaseServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T create(T entity) {
        return repository.save(entity);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + id)); // 统一的异常处理
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T update(ID id, T entity) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Resource not found with id: " + id);
        }
        return repository.save(entity);
    }

    @Override
    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Resource not found with id: " + id);
        }
        repository.deleteById(id);
    }
}