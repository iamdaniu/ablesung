package de.daniu.home.ablesung;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Ablesung {
    String getMeterId();
    LocalDate getDatum();
    BigDecimal getWert();

    @Getter
    @Builder
    @ToString
    class SimpleAblesung implements Ablesung {
        String meterId;
        LocalDate datum;
        BigDecimal wert;
    }
}
