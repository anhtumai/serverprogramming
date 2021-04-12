package com.example.bookstore.repository;

import com.example.bookstore.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    public Category findByName(String name);
}
