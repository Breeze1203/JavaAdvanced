package org.pengtao.backendabstraction.service.base;

import java.util.List;

public interface IBaseService<T, ID> {
    T create(T entity);
    T findById(ID id);
    List<T> findAll();
    T update(ID id, T entity);
    void deleteById(ID id);
}
