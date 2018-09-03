package de.daniu.home.ablesung.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AblesungRepository extends CrudRepository<AblesungEntity,Long> {
    List<AblesungEntity> findByMeterIdAndDatumBetween(String meterId, LocalDate von, LocalDate bis);
    List<AblesungEntity> findByDatumBetween(LocalDate von, LocalDate bis);
}
