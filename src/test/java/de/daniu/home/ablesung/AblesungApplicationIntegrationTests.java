package de.daniu.home.ablesung;

import de.daniu.home.ablesung.db.DbConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AblesungApplicationIntegrationTests {
	@Autowired
	private AblesungService ablesungService;

	@Autowired
    private DbConfiguration dbConfiguration;

	@Test
	public void contextLoads() {
	}

	@Ignore // only works when config server is running, to test db access
	@Test
	public void testDb() throws Exception {
		try (Connection conn = createConnection();
            Statement statement = conn.createStatement()) {
            statement.execute("drop table if exists test_table");
            statement.execute("create table test_table (meter_id varchar(100), value decimal, datum date)");
            int columns = statement.executeUpdate("insert into test_table values('test_meter', 123.45, '2018-12-31')");
            assertThat(columns).isEqualTo(1);
            columns = statement.executeUpdate("delete from test_table where meter_id = 'test_meter'");
            assertThat(columns).isEqualTo(1);
            statement.execute("drop table test_table");
        }
	}

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(dbConfiguration.getUrl(), dbConfiguration.getUsername(), dbConfiguration.getPassword());
    }

    @Ignore // we should have a separate test db to test this, set up and add to config
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
