package fr.vertours.poseidon.controller;

import fr.vertours.poseidon.domain.Trade;
import fr.vertours.poseidon.dto.TradeDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.service.ITradeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import static fr.vertours.poseidon.utils.FakeTrade.getDTO1;
import static fr.vertours.poseidon.utils.FakeTrade.getEntity1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TradeControllerTest {

    @Autowired
    MockMvc mvc;


    ITradeService service = mock(ITradeService.class);

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    TradeController classUnderTest = new TradeController(service);


    @Test
    void home() throws Exception {
        mvc.perform(get("/trade/list"))
                .andExpect(status().isFound()); // FOUND : 302

    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void homeWithSpringSecurity() throws Exception {

        mvc.perform(get("/trade/list"))
                .andExpect(status().isOk()) // SUCCEED : 200
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("list"))
        ;
    }

    @Test
    void addTradeForm() {
        TradeDTO dto = getDTO1();

        String tested = classUnderTest.addTradeForm(dto);

        assertEquals("trade/add", tested);
    }

    @Test
    void validate() {
        TradeDTO dto = getDTO1();
        doNothing().when(service).save(any(TradeDTO.class));
        when(bindingResult.hasErrors()).thenReturn(false);

        String tested = classUnderTest.validate(dto, bindingResult, model);

        verify(service, times(1)).save(any(TradeDTO.class));
        verify(service, times(1)).findAll();
        assertEquals("trade/list", tested);
    }

    @Test
    void validateBindingErrors() {
        TradeDTO dto = getDTO1();
        when(bindingResult.hasErrors()).thenReturn(true);

        String tested = classUnderTest.validate(dto, bindingResult, model);

        assertEquals("trade/add", tested);
    }

    @Test
    void showUpdateForm() {
        TradeDTO dto = getDTO1();
        Trade entity = getEntity1();
        when(service.findId(5)).thenReturn(entity);

        String tested = classUnderTest.showUpdateForm(5, model);

        assertEquals("trade/update", tested);
        verify(service, times(1)).findId(5);
    }

    @Test
    void showUpdateFormBindingException() {
        TradeDTO dto = getDTO1();
        Trade entity = getEntity1();
        when(service.findId(50)).thenThrow(InvalidIDException.class);

        String tested = classUnderTest.showUpdateForm(50, model);

        assertEquals("404", tested);
    }

    @Test
    void updateTrade() {
        TradeDTO dto = getDTO1();
        doNothing().when(service).updateId(any(Integer.class), any(TradeDTO.class));
        when(bindingResult.hasErrors()).thenReturn(false);

        String tested = classUnderTest.updateTrade(25, dto, bindingResult, model);

        assertEquals("redirect:/trade/list", tested);
    }

    @Test
    void updateTradeErrors() {
        TradeDTO dto = getDTO1();
        when(bindingResult.hasErrors()).thenReturn(true);

        String tested = classUnderTest.updateTrade(25, dto, bindingResult, model);

        assertEquals("trade/update", tested);

    }

    @Test
    void deleteTrade() {
        Trade entity = getEntity1();
        when(service.findId(25)).thenReturn(entity);

        String tested = classUnderTest.deleteTrade(25, model);

        assertEquals("redirect:/trade/list", tested);

    }

    @Test
    void deleteTradeException() {
        when(service.findId(50)).thenThrow(InvalidIDException.class);

        String tested = classUnderTest.deleteTrade(50, model);

        assertEquals("404", tested);

    }

}