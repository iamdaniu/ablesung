package de.daniu.home.ablesung.service;

import de.daniu.home.ablesung.Ablesung;
import de.daniu.home.ablesung.AblesungService;
import de.daniu.home.ablesung.AblesungsArt;
import de.daniu.home.ablesung.db.AblesungRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Stream;

public class RepositoryAblesungService implements AblesungService {
    private static final AblesungEntityMapper MAPPER = Mappers.getMapper(AblesungEntityMapper.class);

    @Autowired
    private AblesungRepository repository;
    private final AblesungsArt art;

    RepositoryAblesungService(AblesungsArt art) {
        this.art = art;
    }

    @Override
    public void addAblesung(Ablesung ablesung) {
        repository.save(MAPPER.from(ablesung));
    }

    public Stream<Ablesung> getAblesungen(LocalDate von, LocalDate bis) {
        return repository.streamByArtAndDatumBetween(art, von, bis)
                .map(Function.identity());
    }
}
