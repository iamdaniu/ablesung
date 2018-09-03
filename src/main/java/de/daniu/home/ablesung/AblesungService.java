package de.daniu.home.ablesung;

import java.time.LocalDate;
import java.util.List;

public interface AblesungService {
    void addAblesung(Ablesung ablesung);
    List<Ablesung> getAblesungen(LocalDate von, LocalDate bis);
    List<Ablesung> getAblesungen(String meterId, LocalDate von, LocalDate bis);
}
