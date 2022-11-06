package com.lisovenko.offeraid.repositories;

import com.lisovenko.offeraid.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAll();

    Optional<Category> findByUrl(String url);
}

