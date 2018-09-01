package de.daniu.home.ablesung;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface AblesungService {
    void addAblesung(Ablesung ablesung);
    Stream<Ablesung> getAblesungen(LocalDate von, LocalDate bis);
}
