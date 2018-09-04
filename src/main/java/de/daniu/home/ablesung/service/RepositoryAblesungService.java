package de.daniu.home.ablesung.service;

import de.daniu.home.ablesung.Ablesung;
import de.daniu.home.ablesung.AblesungService;
import de.daniu.home.ablesung.db.AblesungEntity;
import de.daniu.home.ablesung.db.AblesungRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class RepositoryAblesungService implements AblesungService {
    private static final AblesungEntityMapper MAPPER = Mappers.getMapper(AblesungEntityMapper.class);

    @Autowired
    private AblesungRepository repository;

    @Override
    public void addAblesung(Ablesung ablesung) {
        AblesungEntity entity = MAPPER.from(ablesung);
        repository.save(entity);
    }

    @Override
    public List<Ablesung> getAblesungen(LocalDate von, LocalDate bis) {
        return new ArrayList<>(repository.findByDatumBetween(von, bis));
    }

    @Override
    public List<Ablesung> getAblesungen(String meterId, LocalDate von, LocalDate bis) {
        return new ArrayList<>(repository.findByMeterIdAndDatumBetween(meterId, von, bis));
    }
}
