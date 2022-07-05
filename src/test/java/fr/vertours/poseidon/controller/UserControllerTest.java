package fr.vertours.poseidon.controller;


import fr.vertours.poseidon.domain.User;

import fr.vertours.poseidon.dto.UserDTO;
import fr.vertours.poseidon.exception.InvalidIDException;

import fr.vertours.poseidon.repository.UserRepository;
import fr.vertours.poseidon.service.IUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static fr.vertours.poseidon.utils.FakeUsers.getDTO1;
import static fr.vertours.poseidon.utils.FakeUsers.getEntity1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    UserRepository userRepository = mock(UserRepository.class);
    IUserService service = mock(IUserService.class);

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    UserController classUnderTest = new UserController(userRepository, service);


    @Test
    void home() throws Exception {
        mvc.perform(get("/user/list"))
                .andExpect(status().isFound()); // FOUND : 302

    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void homeWithSpringSecurity() throws Exception {

        mvc.perform(get("/user/list"))
                .andExpect(status().isOk()) // SUCCEED : 200
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
        ;
    }

    @Test
    void addUserForm() {
        UserDTO dto = getDTO1();

        String tested = classUnderTest.addUserForm(dto);

        assertEquals("user/add", tested);
    }

    @Test
    void validate() {
        UserDTO dto = getDTO1();
        doNothing().when(service).save(any(UserDTO.class));
        when(bindingResult.hasErrors()).thenReturn(false);

        String tested = classUnderTest.validate(dto, bindingResult, model);

        verify(service, times(1)).save(any(UserDTO.class));
        verify(service, times(1)).findAll();
        assertEquals("/user/list", tested);
    }

    @Test
    void validateBindingErrors() {
        UserDTO dto = getDTO1();
        when(bindingResult.hasErrors()).thenReturn(true);

        String tested = classUnderTest.validate(dto, bindingResult, model);

        assertEquals("user/add", tested);
    }

    @Test
    void showUpdateForm() {
        UserDTO dto = getDTO1();
        User entity = getEntity1();
        when(service.findId(5)).thenReturn(entity);

        String tested = classUnderTest.showUpdateForm(5, model);

        assertEquals("user/update", tested);
        verify(service, times(1)).findId(5);
    }

    @Test
    void showUpdateFormBindingException() {
        UserDTO dto = getDTO1();
        User entity = getEntity1();
        when(service.findId(50)).thenThrow(InvalidIDException.class);

        String tested = classUnderTest.showUpdateForm(50, model);

        assertEquals("404", tested);
    }

    @Test
    void updateUser() {
        UserDTO dto = getDTO1();
        doNothing().when(service).updateId(any(Integer.class), any(UserDTO.class));
        when(bindingResult.hasErrors()).thenReturn(false);

        String tested = classUnderTest.updateUser(25, dto, bindingResult, model);

        assertEquals("redirect:/user/list", tested);
    }

    @Test
    void updateUserErrors() {
        UserDTO dto = getDTO1();
        when(bindingResult.hasErrors()).thenReturn(true);

        String tested = classUnderTest.updateUser(25, dto, bindingResult, model);

        assertEquals("user/update", tested);

    }

    @Test
    void deleteUser() {
        User entity = getEntity1();
        when(service.findId(25)).thenReturn(entity);

        String tested = classUnderTest.deleteUser(25, model);

        assertEquals("redirect:/user/list", tested);

    }

    @Test
    void deleteUserException() {
        when(service.findId(50)).thenThrow(InvalidIDException.class);

        String tested = classUnderTest.deleteUser(50, model);

        assertEquals("404", tested);

    }

}