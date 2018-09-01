package de.daniu.home.ablesung.db;

import de.daniu.home.ablesung.AblesungsArt;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface AblesungRepository extends CrudRepository<AblesungEntity,Long> {
    Stream<AblesungEntity> streamByArtAndDatumBetween(AblesungsArt art, LocalDate von, LocalDate bis);
}
