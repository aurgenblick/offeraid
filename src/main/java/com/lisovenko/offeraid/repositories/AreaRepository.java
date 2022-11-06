package com.lisovenko.offeraid.repositories;

import com.lisovenko.offeraid.entities.Area;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AreaRepository extends CrudRepository<Area, Integer> {
  List<Area> findAll();

  Optional<Area> findByUrl(String url);

  @Query(
      "select a.id from Area a where a.url = :url"
          + " order by a.id")
  List<Integer> findAreas(String url);
}
