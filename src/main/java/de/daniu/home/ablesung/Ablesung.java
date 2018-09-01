package de.daniu.home.ablesung;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Ablesung {
    LocalDate getDatum();
    BigDecimal getWert();
}
