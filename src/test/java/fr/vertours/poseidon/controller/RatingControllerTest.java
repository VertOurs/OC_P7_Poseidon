package fr.vertours.poseidon.controller;

import fr.vertours.poseidon.domain.Rating;
import fr.vertours.poseidon.dto.RatingDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.service.IRatingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import static fr.vertours.poseidon.utils.FakeRating.getDTO1;
import static fr.vertours.poseidon.utils.FakeRating.getEntity1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RatingControllerTest {

    @Autowired
    MockMvc mvc;

    IRatingService service = mock(IRatingService.class);

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    RatingController classUnderTest = new RatingController(service);

    @Test
    void home() throws Exception {
        mvc.perform(get("/rating/list"))
                .andExpect(status().isFound()); // FOUND : 302
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void homeWithSpringSecurity() throws Exception {

        mvc.perform(get("/rating/list"))
                .andExpect(status().isOk()) // SUCCEED : 200
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("list"))
        ;
    }

    @Test
    void addRatingForm() {
        RatingDTO dto = getDTO1();

        String tested = classUnderTest.addRatingForm(dto);

        assertEquals("rating/add", tested);
    }

    @Test
    void validate() {
        RatingDTO dto = getDTO1();
        doNothing().when(service).save(any(RatingDTO.class));
        when(bindingResult.hasErrors()).thenReturn(false);

        String tested = classUnderTest.validate(dto, bindingResult, model);

        verify(service, times(1)).save(any(RatingDTO.class));
        verify(service, times(1)).findAll();
        assertEquals("rating/list", tested);
    }

    @Test
    void validateBindingErrors() {
        RatingDTO dto = getDTO1();
        when(bindingResult.hasErrors()).thenReturn(true);

        String tested = classUnderTest.validate(dto, bindingResult, model);

        assertEquals("rating/add", tested);
    }

    @Test
    void showUpdateForm() {
        RatingDTO dto = getDTO1();
        Rating entity = getEntity1();
        when(service.findId(5)).thenReturn(entity);

        String tested = classUnderTest.showUpdateForm(5, model, dto);

        assertEquals("rating/update", tested);
        verify(service, times(1)).findId(5);
    }

    @Test
    void showUpdateFormBindingException() {
        RatingDTO dto = getDTO1();
        Rating entity = getEntity1();
        when(service.findId(50)).thenThrow(InvalidIDException.class);

        String tested = classUnderTest.showUpdateForm(50, model, dto);

        assertEquals("404", tested);
    }

    @Test
    void updateRating() {
        RatingDTO dto = getDTO1();
        doNothing().when(service).updateId(any(Integer.class), any(RatingDTO.class));
        when(bindingResult.hasErrors()).thenReturn(false);

        String tested = classUnderTest.updateRating(25, dto, bindingResult, model);

        assertEquals("redirect:/rating/list", tested);
    }

    @Test
    void updateRatingErrors() {
        RatingDTO dto = getDTO1();
        when(bindingResult.hasErrors()).thenReturn(true);

        String tested = classUnderTest.updateRating(25, dto, bindingResult, model);

        assertEquals("rating/update", tested);

    }

    @Test
    void deleteRating() {
        Rating entity = getEntity1();
        when(service.findId(25)).thenReturn(entity);

        String tested = classUnderTest.deleteRating(25, model);

        assertEquals("redirect:/rating/list", tested);
    }

    @Test
    void deleteRatingException() {
        when(service.findId(50)).thenThrow(InvalidIDException.class);

        String tested = classUnderTest.deleteRating(50, model);

        assertEquals("404", tested);

    }
}