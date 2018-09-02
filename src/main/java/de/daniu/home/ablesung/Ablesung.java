package de.daniu.home.ablesung;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Ablesung {
    String getMeterId();
    LocalDate getDatum();
    BigDecimal getWert();
}
