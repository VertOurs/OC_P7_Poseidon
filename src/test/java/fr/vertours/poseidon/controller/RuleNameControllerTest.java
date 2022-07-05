package fr.vertours.poseidon.controller;


import fr.vertours.poseidon.domain.RuleName;
import fr.vertours.poseidon.dto.RuleNameDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.service.IRuleNameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import static fr.vertours.poseidon.utils.FakeRuleName.getDTO1;
import static fr.vertours.poseidon.utils.FakeRuleName.getEntity1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RuleNameControllerTest {

    @Autowired
    MockMvc mvc;


    IRuleNameService service = mock(IRuleNameService.class);

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    RuleNameController classUnderTest = new RuleNameController(service);

    @Test
    void home() throws Exception {
        mvc.perform(get("/ruleName/list"))
                .andExpect(status().isFound()); // FOUND : 302

    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void homeWithSpringSecurity() throws Exception {

        mvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk()) // SUCCEED : 200
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("list"))
        ;
    }

    @Test
    void addRatingForm() {
        RuleNameDTO dto = getDTO1();

        String tested = classUnderTest.addRuleForm(dto);

        assertEquals("ruleName/add", tested);
    }

    @Test
    void validate() {
        RuleNameDTO dto = getDTO1();
        doNothing().when(service).save(any(RuleNameDTO.class));
        when(bindingResult.hasErrors()).thenReturn(false);

        String tested = classUnderTest.validate(dto, bindingResult, model);

        verify(service, times(1)).save(any(RuleNameDTO.class));
        verify(service, times(1)).findAll();
        assertEquals("ruleName/list", tested);
    }

    @Test
    void validateBindingErrors() {
        RuleNameDTO dto = getDTO1();
        when(bindingResult.hasErrors()).thenReturn(true);

        String tested = classUnderTest.validate(dto, bindingResult, model);

        assertEquals("ruleName/add", tested);
    }

    @Test
    void showUpdateForm() {
        RuleNameDTO dto = getDTO1();
        RuleName entity = getEntity1();
        when(service.findId(5)).thenReturn(entity);

        String tested = classUnderTest.showUpdateForm(5, model);

        assertEquals("ruleName/update", tested);
        verify(service, times(1)).findId(5);
    }

    @Test
    void showUpdateFormBindingException() {
        RuleNameDTO dto = getDTO1();
        RuleName entity = getEntity1();
        when(service.findId(50)).thenThrow(InvalidIDException.class);

        String tested = classUnderTest.showUpdateForm(50, model);

        assertEquals("404", tested);
    }

    @Test
    void updateRuleName() {
        RuleNameDTO dto = getDTO1();
        doNothing().when(service).updateId(any(Integer.class), any(RuleNameDTO.class));
        when(bindingResult.hasErrors()).thenReturn(false);

        String tested = classUnderTest.updateRuleName(25, dto, bindingResult, model);

        assertEquals("redirect:/ruleName/list", tested);
    }

    @Test
    void updateRuleNameErrors() {
        RuleNameDTO dto = getDTO1();
        when(bindingResult.hasErrors()).thenReturn(true);

        String tested = classUnderTest.updateRuleName(25, dto, bindingResult, model);

        assertEquals("ruleName/update", tested);

    }

    @Test
    void deleteRuleName() {
        RuleName entity = getEntity1();
        when(service.findId(25)).thenReturn(entity);

        String tested = classUnderTest.deleteRuleName(25, model);

        assertEquals("redirect:/ruleName/list", tested);

    }

    @Test
    void deleteRuleNameException() {
        when(service.findId(50)).thenThrow(InvalidIDException.class);

        String tested = classUnderTest.deleteRuleName(50, model);

        assertEquals("404", tested);

    }
}