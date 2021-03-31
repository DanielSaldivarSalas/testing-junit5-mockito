package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceBDDTest {
    Visit visit;

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService visitSDJpaService;

    @BeforeEach
    void setUp() {
        visit = new Visit();
    }

    @Test
    void findAllBDDTest() {
        //given
        Set<Visit> visits = Set.of(visit);
        given(visitRepository.findAll()).willReturn(visits);

        //when
        Set<Visit> foundVisits = visitSDJpaService.findAll();

        //then
        assertThat(foundVisits).isNotNull();
        assertThat(foundVisits.size()).isEqualTo(1);
        then(visitRepository).should().findAll();
        then(visitRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void findByIdBddTest() {
        //given
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));

        //when
        Visit foundVisit = visitSDJpaService.findById(anyLong());

        //then
        assertThat(foundVisit).isNotNull();
        then(visitRepository).should().findById(anyLong());
        then(visitRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void saveBddTest() {
        //given
        given(visitRepository.save(any(Visit.class))).willReturn(visit);

        //when
        Visit savedVisit = visitSDJpaService.save(visit);

        //then
        assertThat(savedVisit).isNotNull();
        then(visitRepository).should().save(any(Visit.class));
        then(visitRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteBddTest() {
        //given

        //when
        visitSDJpaService.delete(visit);

        //then
        then(visitRepository).should().delete(any(Visit.class));
        then(visitRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteByIdBddTest() {
        //given

        //when
        visitSDJpaService.deleteById(anyLong());

        //then
        then(visitRepository).should().deleteById(anyLong());
        then(visitRepository).shouldHaveNoMoreInteractions();
    }
}