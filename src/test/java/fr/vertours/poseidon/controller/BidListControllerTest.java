package fr.vertours.poseidon.controller;

import fr.vertours.poseidon.domain.BidList;
import fr.vertours.poseidon.dto.BidListDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.service.IBidListService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;



import static fr.vertours.poseidon.utils.FakeBidList.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BidListControllerTest {

    @Autowired
    MockMvc mvc;


    IBidListService service = mock(IBidListService.class);

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    BidListController classUnderTest = new BidListController(service);


    @Test
    void home() throws Exception {
        mvc.perform(get("/bidList/list"))
                .andExpect(status().isFound()); // FOUND : 302

    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void homeWithSpringSecurity() throws Exception {

        mvc.perform(get("/bidList/list"))
                .andExpect(status().isOk()) // SUCCEED : 200
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("list"))
        ;
    }

    @Test
    void addBidForm() {
        BidListDTO dto = getDTO1();

        String tested = classUnderTest.addBidForm(dto);

        assertEquals("bidList/add", tested);
    }

    @Test
    void validate() {
        BidListDTO dto = getDTO1();
        doNothing().when(service).save(any(BidListDTO.class));
        when(bindingResult.hasErrors()).thenReturn(false);

        String tested = classUnderTest.validate(dto, bindingResult, model);

        verify(service, times(1)).save(any(BidListDTO.class));
        verify(service, times(1)).findAll();
        assertEquals("bidList/list", tested);
    }

    @Test
    void validateBindingErrors() {
        BidListDTO dto = getDTO1();
        when(bindingResult.hasErrors()).thenReturn(true);

        String tested = classUnderTest.validate(dto, bindingResult, model);

        assertEquals("bidList/add", tested);
    }

    @Test
    void showUpdateForm() {
        BidListDTO dto = getDTO1();
        BidList entity = getEntity1();
        when(service.findId(5)).thenReturn(entity);

        String tested = classUnderTest.showUpdateForm(5, model, dto);

        assertEquals("bidList/update", tested);
        verify(service, times(1)).findId(5);
    }

    @Test
    void showUpdateFormBindingException() {
        BidListDTO dto = getDTO1();
        BidList entity = getEntity1();
        when(service.findId(50)).thenThrow(InvalidIDException.class);

        String tested = classUnderTest.showUpdateForm(50, model, dto);

        assertEquals("404", tested);
    }

    @Test
    void updateBid() {
        BidListDTO dto = getDTO1();
        doNothing().when(service).updateId(any(Integer.class), any(BidListDTO.class));
        when(bindingResult.hasErrors()).thenReturn(false);

        String tested = classUnderTest.updateBid(25, dto, bindingResult, model);

        assertEquals("redirect:/bidList/list", tested);
    }

    @Test
    void updateBidErrors() {
        BidListDTO dto = getDTO1();
        when(bindingResult.hasErrors()).thenReturn(true);

        String tested = classUnderTest.updateBid(25, dto, bindingResult, model);

        assertEquals("bidList/update", tested);

    }

    @Test
    void deleteBid() {
        BidList entity = getEntity1();
        when(service.findId(25)).thenReturn(entity);

        String tested = classUnderTest.deleteBid(25, model);

        assertEquals("redirect:/bidList/list", tested);

    }

    @Test
    void deleteBidException() {
        when(service.findId(50)).thenThrow(InvalidIDException.class);

        String tested = classUnderTest.deleteBid(50, model);

        assertEquals("404", tested);

    }
}