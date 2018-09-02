package de.daniu.home.ablesung.service;

import de.daniu.home.ablesung.Ablesung;
import de.daniu.home.ablesung.AblesungService;
import de.daniu.home.ablesung.db.AblesungRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class RepositoryAblesungService implements AblesungService {
    private static final AblesungEntityMapper MAPPER = Mappers.getMapper(AblesungEntityMapper.class);

    @Autowired
    private AblesungRepository repository;

    @Override
    public void addAblesung(Ablesung ablesung) {
        repository.save(MAPPER.from(ablesung));
    }

    public Stream<Ablesung> getAblesungen(LocalDate von, LocalDate bis) {
        return repository.streamByDatumBetween(von, bis)
                .map(Function.identity());
    }
    public Stream<Ablesung> getAblesungen(String meterId, LocalDate von, LocalDate bis) {
        return repository.streamByMeterIdAndDatumBetween(meterId, von, bis)
                .map(Function.identity());
    }
}
