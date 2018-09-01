package de.daniu.home.ablesung.db;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface AblesungRepository extends CrudRepository<AblesungEntity,Long> {
    List<AblesungEntity> findByDatumBetween(LocalDate von, LocalDate bis);
}
