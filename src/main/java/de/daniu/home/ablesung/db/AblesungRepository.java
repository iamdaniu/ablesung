package de.daniu.home.ablesung.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.stream.Stream;

@Repository
public interface AblesungRepository extends CrudRepository<AblesungEntity,Long> {
    Stream<AblesungEntity> streamByMeterIdAndDatumBetween(String meterId, LocalDate von, LocalDate bis);
    Stream<AblesungEntity> streamByDatumBetween(LocalDate von, LocalDate bis);
}
