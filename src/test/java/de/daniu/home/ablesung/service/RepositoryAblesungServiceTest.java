package de.daniu.home.ablesung.service;

import de.daniu.home.ablesung.Ablesung;
import de.daniu.home.ablesung.db.AblesungEntity;
import de.daniu.home.ablesung.db.AblesungRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryAblesungServiceTest {
    @InjectMocks
    private RepositoryAblesungService ablesungService;

    @Mock
    private AblesungEntityMapper mapper;
    @Mock
    private AblesungRepository repository;

    private final LocalDate von = LocalDate.of(2018, 9, 1);
    private final LocalDate bis = LocalDate.of(2018, 9, 30);

    @Test
    public void addAblesung() {
        Ablesung ablesung = mock(Ablesung.class);
        AblesungEntity ablesungEntity = mock(AblesungEntity.class);
        when(mapper.from(ablesung)).thenReturn(ablesungEntity);
        ArgumentCaptor<AblesungEntity> savedCaptor = ArgumentCaptor.forClass(AblesungEntity.class);

        ablesungService.addAblesung(ablesung);

        verify(repository).save(savedCaptor.capture());
        assertThat(savedCaptor.getValue()).isSameAs(ablesungEntity);
    }

    @Test
    public void getAblesungenByDate() {
        List<AblesungEntity> fromRepo = Arrays.asList(mock(AblesungEntity.class), mock(AblesungEntity.class), mock(AblesungEntity.class));
        LocalDate von = LocalDate.of(2018, 9, 1);
        LocalDate bis = LocalDate.of(2018, 9, 30);
        when(repository.findByDatumBetween(von, bis)).thenReturn(fromRepo);

        List<Ablesung> ablesungen = ablesungService.getAblesungen(von, bis);

        verify(repository).findByDatumBetween(von, bis);
        assertThat(ablesungen).isEqualTo(fromRepo);
    }

    @Test
    public void getAblesungenByMeterAndDate() {
        List<AblesungEntity> fromRepo = Arrays.asList(mock(AblesungEntity.class), mock(AblesungEntity.class), mock(AblesungEntity.class));
        String meterId = "meter";
        when(repository.findByMeterIdAndDatumBetween(meterId, von, bis)).thenReturn(fromRepo);

        List<Ablesung> ablesungen = ablesungService.getAblesungen(meterId, von, bis);

        verify(repository).findByMeterIdAndDatumBetween(meterId, von, bis);
        assertThat(ablesungen).isEqualTo(fromRepo);
    }
}