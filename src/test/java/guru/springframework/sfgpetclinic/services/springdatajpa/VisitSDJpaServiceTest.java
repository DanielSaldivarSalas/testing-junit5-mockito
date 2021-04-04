package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    private Visit visit;

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService visitSDJpaService;
    @BeforeEach
    void setUp() {
        visit = new Visit();
    }

    @Test
    void findAllTest() {
        Set<Visit> visitSet = Set.of(visit);


        when(visitSDJpaService.findAll()).thenReturn(visitSet);
        Set<Visit> visits = visitSDJpaService.findAll();
        assertThat(visits.size()).isEqualTo(1);
        verify(visitRepository).findAll();

    }

    @Test
    void findByIdTest() {
        when(visitRepository.findById(1L)).thenReturn(Optional.of(visit));
        Visit foundVisit = visitSDJpaService.findById(1L);
        assertThat(foundVisit).isNotNull();
        verify(visitRepository).findById(anyLong());
    }

    @Test
    void saveTest() {
        when(visitRepository.save(visit)).thenReturn(visit);
        Visit foundVisit = visitSDJpaService.save(visit);
        assertThat(foundVisit).isNotNull();
        verify(visitRepository).save(any(Visit.class));
    }

    @Test
    void deleteTest() {
        visitSDJpaService.delete(visit);
        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteByIdTest() {
        visitSDJpaService.deleteById(anyLong());
        verify(visitRepository).deleteById(anyLong());
    }
}