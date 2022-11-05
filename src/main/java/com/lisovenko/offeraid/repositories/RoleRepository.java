package com.lisovenko.offeraid.repositories;

import com.lisovenko.offeraid.entities.Role;
import com.lisovenko.offeraid.security.RoleAuth;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RoleRepository extends CrudRepository<Role, Integer> {
  @EntityGraph(attributePaths = {"users"})
  Optional<Role> findByName(RoleAuth role);
}
