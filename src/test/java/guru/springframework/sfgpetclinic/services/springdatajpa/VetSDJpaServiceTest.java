package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {
    @Mock
    private Vet vet;
    @Mock
    private VetRepository vetRepository;

    @InjectMocks
    private VetSDJpaService vetSDJpaService;

    @Test
    void deleteByIdTest() {
        vetSDJpaService.deleteById(1L);
        verify(vetRepository).deleteById(1L);
    }

    @Test
    void findAllTest() {
        Set<Vet> vetSet = new HashSet<>();
        vetSet.add(vet);
        when(vetRepository.findAll()).thenReturn(vetSet);
        Set<Vet> foundSet = vetSDJpaService.findAll();
        assertThat(foundSet.size())
            .isEqualTo(1);
        assertThat(foundSet).isNotNull();
        verify(vetRepository).findAll();
    }

    @Test
    void findByIdTest() {
        when(vetRepository.findById(1L)).thenReturn(Optional.of(vet));
        Vet foundVet = vetSDJpaService.findById(1L);
        assertThat(foundVet).isNotNull();
        verify(vetRepository).findById(anyLong());
    }

    @Test
    void saveTest() {

        when(vetRepository.save(vet)).thenReturn(vet);
        Vet savedVet = vetSDJpaService.save(vet);
        assertThat(savedVet).isNotNull();
        assertThat(savedVet).isInstanceOf(Vet.class);
        verify(vetRepository).save(any(Vet.class));
    }

    @Test
    void deleteTest() {
        vetSDJpaService.delete(vet);
        verify(vetRepository).delete(any(Vet.class));
    }

}