package de.daniu.home.ablesung.service;

import de.daniu.home.ablesung.Ablesung;
import de.daniu.home.ablesung.db.AblesungEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AblesungEntityMapper {
    AblesungEntity from(Ablesung ablesung);
}
