package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {
    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks //this will inject hte specialtyRepository mock into the speciality service!
    SpecialitySDJpaService service;

    //Makes sure functiosn gets called only once
    @Test
    void deleteByIdTest() {
        service.deleteById(1L);
        verify(specialtyRepository).deleteById(1L);
    }

    //Make sure this gets called only twice
    @Test
    void deleteByIdTimesTest() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, times(2)).deleteById(1L);
    }
    //Makes sure function gets called at least once
    @Test
    void deleteByIdAtLeastOnceTest() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
    }

    //Makes sure functions gets called at most x times
    @Test
    void deleteByIdAtMostTest() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNeverTest() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
        verify(specialtyRepository, never()).deleteById(5L); //Makes sure it doesn't get called with 5L
    }

    @Test
    void deleteByObjectTest(){
        Speciality speciality = new Speciality();
        service.delete(speciality);
        verify(specialtyRepository).delete(any(Speciality.class));
        //this is using matchers, makes sure a Speciality class was passed
    }

    @Test
    void findByIdTest(){
        Speciality speciality = new Speciality();

        when(specialtyRepository.findById(1L))
            .thenReturn(Optional.of(speciality));
        Speciality foundSpecialty = service.findById(1L);

        assertThat(foundSpecialty).isNotNull();
        verify(specialtyRepository).findById(anyLong());
        //verify(specialtyRepository).findById(1L); without matchers
    }
}