package com.lisovenko.offeraid.repositories;

import com.lisovenko.offeraid.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<User, Integer> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String username);
    boolean existsByEmail(String email);
}
