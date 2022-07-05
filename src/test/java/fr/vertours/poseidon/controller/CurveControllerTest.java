package fr.vertours.poseidon.controller;

import fr.vertours.poseidon.domain.CurvePoint;
import fr.vertours.poseidon.dto.CurvePointDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.service.ICurveService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static fr.vertours.poseidon.utils.FakeCurvePoint.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CurveControllerTest {

    @Autowired
    MockMvc mvc;

    ICurveService service = mock(ICurveService.class);

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    CurveController classUnderTest = new CurveController(service);

    @Test
    void home() throws Exception {
        mvc.perform(get("/curvePoint/list"))
                .andExpect(status().isFound()); // FOUND : 302
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void homeWithSpringSecurity() throws Exception {

        mvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk()) // SUCCEED : 200
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("list"))
        ;
    }

    @Test
    void addBidForm() {
        CurvePointDTO dto = getDTO1();

        String tested = classUnderTest.addBidForm(dto);

        assertEquals("curvePoint/add", tested);
    }

    @Test
    void validate() {
        CurvePointDTO dto = getDTO1();
        doNothing().when(service).save(any(CurvePointDTO.class));
        when(bindingResult.hasErrors()).thenReturn(false);

        String tested = classUnderTest.validate(dto, bindingResult, model);

        verify(service, times(1)).save(any(CurvePointDTO.class));
        verify(service, times(1)).findAll();
        assertEquals("curvePoint/list", tested);

    }

    @Test
    void validateBindingErrors() {
        CurvePointDTO dto = getDTO1();
        when(bindingResult.hasErrors()).thenReturn(true);

        String tested = classUnderTest.validate(dto, bindingResult, model);

        assertEquals("curvePoint/add", tested);
    }

    @Test
    void showUpdateForm() {
        CurvePointDTO dto = getDTO1();
        CurvePoint entity = getEntity1();
        when(service.findId(5)).thenReturn(entity);

        String tested = classUnderTest.showUpdateForm(5, model);

        assertEquals("curvePoint/update", tested);
        verify(service, times(1)).findId(5);
    }

    @Test
    void showUpdateFormBindingException() {
        CurvePointDTO dto = getDTO1();
        CurvePoint entity = getEntity1();
        when(service.findId(50)).thenThrow(InvalidIDException.class);

        String tested = classUnderTest.showUpdateForm(50, model);

        assertEquals("404", tested);
    }

    @Test
    void updateBid() {
        CurvePointDTO dto = getDTO1();
        doNothing().when(service).updateId(any(Integer.class), any(CurvePointDTO.class));
        when(bindingResult.hasErrors()).thenReturn(false);

        String tested = classUnderTest.updateBid(25, dto, bindingResult, model);

        assertEquals("redirect:/curvePoint/list", tested);
    }

    @Test
    void updateBidErrors() {
        CurvePointDTO dto = getDTO1();
        when(bindingResult.hasErrors()).thenReturn(true);

        String tested = classUnderTest.updateBid(25, dto, bindingResult, model);

        assertEquals("curvePoint/update", tested);

    }

    @Test
    void deleteBid() {
        CurvePoint entity = getEntity1();
        when(service.findId(25)).thenReturn(entity);

        String tested = classUnderTest.deleteBid(25, model);

        assertEquals("redirect:/curvePoint/list", tested);
    }
}