package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_1 = "redirect:/owners/1";

    @Mock
    BindingResult bindingResult;

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController controller;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
            .willAnswer(invocation -> {
               String name = invocation.getArgument(0);
               List<Owner> owners = new ArrayList<>();
               if (name.equals("%Buck%")){
                   owners.add(new Owner(1L, "Joe", "Buck"));
               }
               throw new RuntimeException("Invalid Argument");
            });
    }

    @Test
    void processFindFormsWithAnnotationCaptor(){
        //given
        Owner owner = new Owner(1L, "Joe", "Buck");
        List<Owner> ownerList = new ArrayList<>();
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);
        //when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        //then
        assertThat(stringArgumentCaptor.getValue()).isEqualTo("%Buck%");
    }

    @Test
    @DisplayName("Verify that correct value is returned when result has errors")
    void processCreationFormResultErrorsTest() {
        //given
        Owner owner = new Owner(1L, "Daniel", "Saldivar");
        given(bindingResult.hasErrors()).willReturn(true);

        //when
        String formUrl = controller.processCreationForm(owner, bindingResult);
        //then
        assertThat(formUrl).isEqualTo(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);

    }

    @Test
    void processCreationFormResultWithNoErrorsTest(){

        //given
        Owner owner = new Owner(1L, "Daniel", "Saldivar");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any(Owner.class))).willReturn(owner);

        //when
        String formUrl = controller.processCreationForm(owner, bindingResult);

        //then
        assertThat(formUrl).isEqualTo(REDIRECT_OWNERS_1);
        then(ownerService).should().save(any(Owner.class));
        then(ownerService).shouldHaveNoMoreInteractions();
    }
}