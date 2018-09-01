package de.daniu.home.ablesung.service;

import de.daniu.home.ablesung.Ablesung;
import de.daniu.home.ablesung.AblesungService;
import de.daniu.home.ablesung.db.AblesungRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AblesungServiceImpl implements AblesungService {
    @Autowired
    private AblesungRepository repository;

    @Override
    public void addAblesung(Ablesung ablesung) {
        AblesungEntityMapper mapper = Mappers.getMapper(AblesungEntityMapper.class);
        repository.save(mapper.from(ablesung));
    }

    @Override
    public List<? extends Ablesung> getAblesungen(LocalDate von, LocalDate bis) {
        return repository.findByDatumBetween(von, bis);
    }
}
