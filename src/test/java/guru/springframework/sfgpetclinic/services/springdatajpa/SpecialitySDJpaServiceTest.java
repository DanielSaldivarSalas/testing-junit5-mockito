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
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {
    @Mock(lenient = true)
    SpecialtyRepository specialtyRepository;

    @InjectMocks //this will inject hte specialtyRepository mock into the speciality service!
    SpecialitySDJpaService service;

    @Test
    void deleteByIdTest() {
        service.deleteById(1L);
        verify(specialtyRepository).deleteById(1L);
    }

    @Test
    void deleteByIdBDDTest() {
        //given
        //when
        service.deleteById(1L);

        //then
        then(specialtyRepository).should().deleteById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

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
        //given
        Speciality speciality = new Speciality();

        when(specialtyRepository.findById(1L))
            .thenReturn(Optional.of(speciality));

        //when
        Speciality foundSpecialty = service.findById(1L);

        //then
        assertThat(foundSpecialty).isNotNull();
        verify(specialtyRepository).findById(anyLong());
    }
    @Test
    void findByIdBDDTest(){
        //given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(anyLong()))
                .willReturn(Optional.of(speciality));

        //when
        Speciality foundSpecialty = service.findById(1L);

        //then
        assertThat(foundSpecialty).isNotNull();
        then(specialtyRepository).should(times(1)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();


    }

    @Test
    void throwTest(){
        //given
        Speciality speciality = new Speciality();
        doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any(Speciality.class));

        //then
        assertThatThrownBy(() -> {
                //when
                service.delete(speciality);
            }).isInstanceOf(RuntimeException.class);

        verify(specialtyRepository, times(1)).delete(any(Speciality.class));
        //then(service).should().delete(any());
        //then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void ThrowBDDTest(){

        given(specialtyRepository.findById(1L)).willThrow(new RuntimeException("boom"));

        assertThatThrownBy(() -> {
            service.findById(1L);
        }).isInstanceOf(RuntimeException.class);

        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void voidMethodsBDDTest(){
        //if the method returns a void, given can't be in the front
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());

        assertThatThrownBy(() -> {
            service.delete(new Speciality());
        }).isInstanceOf(RuntimeException.class);


    }

    @Test
    void saveLambdaTest(){
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription(MATCH_ME);

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);

        //need mock only when return on match MATCH_ME string
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME))))
            .willReturn(savedSpeciality);

        //when
        Speciality returnedSpeciality = service.save(speciality);

        //then
        assertThat(returnedSpeciality.getId())
            .isEqualTo(1L);
    }

    @Test
    void saveLambdaNoMatchTest(){
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription("Not a match");

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);

        //need mock only when return on match MATCH_ME string
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME))))
            .willReturn(savedSpeciality);

        //when
        Speciality returnedSpeciality = service.save(speciality);

        //then
        assertThat(returnedSpeciality)
            .isNull();

    }
}
