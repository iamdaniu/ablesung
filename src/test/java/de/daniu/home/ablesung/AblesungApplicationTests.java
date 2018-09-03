package de.daniu.home.ablesung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AblesungApplicationTests {
	@Autowired
	private AblesungService ablesungService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void ablesungStored() {
		Ablesung ablesung = Ablesung.SimpleAblesung.builder()
				.datum(LocalDate.now())
				.meterId("meter")
				.wert(BigDecimal.TEN)
				.build();
		ablesungService.addAblesung(ablesung);

		List<Ablesung> ablesungen = ablesungService.getAblesungen(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
		assertThat(ablesungen.size()).isEqualTo(1);
		Ablesung retrieved = ablesungen.get(0);
		assertThat(retrieved.getDatum()).isEqualTo(ablesung.getDatum());
		assertThat(retrieved.getWert().compareTo(ablesung.getWert())).isEqualTo(0);
		assertThat(retrieved.getMeterId()).isEqualTo(ablesung.getMeterId());
	}
}
