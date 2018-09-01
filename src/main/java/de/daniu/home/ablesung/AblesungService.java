package de.daniu.home.ablesung;

import java.time.LocalDate;
import java.util.List;

public interface AblesungService {
    void addAblesung(Ablesung ablesung);
    List<? extends Ablesung> getAblesungen(LocalDate von, LocalDate bis);
}
