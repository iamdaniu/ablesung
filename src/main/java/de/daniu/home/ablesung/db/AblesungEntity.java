package de.daniu.home.ablesung.db;

import de.daniu.home.ablesung.Ablesung;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AblesungEntity implements Ablesung {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private LocalDate datum;
    @NotNull
    private BigDecimal wert;
}
