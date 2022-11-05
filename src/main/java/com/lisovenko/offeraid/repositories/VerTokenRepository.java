package com.lisovenko.offeraid.repositories;

import com.lisovenko.offeraid.entities.VerToken;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VerTokenRepository extends CrudRepository<VerToken, Integer> {
  @EntityGraph(attributePaths = {"user"})
  Optional<VerToken> findByToken(String token);
}
