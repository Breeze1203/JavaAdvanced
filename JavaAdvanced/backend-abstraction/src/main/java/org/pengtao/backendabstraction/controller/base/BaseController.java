package org.pengtao.backendabstraction.controller.base;

import org.pengtao.backendabstraction.common.ApiResponse;
import org.pengtao.backendabstraction.service.base.IBaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseController<T, ID> {

    private final IBaseService<T, ID> service;

    public BaseController(IBaseService<T, ID> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<T>> create(@RequestBody T entity) {
        return ResponseEntity.ok(ApiResponse.success(service.create(entity)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<T>> getById(@PathVariable ID id) {
        return ResponseEntity.ok(ApiResponse.success(service.findById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<java.util.List<T>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(service.findAll()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<T>> update(@PathVariable ID id, @RequestBody T entity) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, entity)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable ID id) {
        service.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}