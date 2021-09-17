package com.tochko.test_project.service;

public interface CommonInterface<T> {
    T save(T value);
    void delete(T value);
    T findById(Long id);
}
