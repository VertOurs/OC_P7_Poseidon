package fr.vertours.poseidon.controller;

import fr.vertours.poseidon.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    UserRepository repository = mock(UserRepository.class);

    LoginController classUnderTest = new LoginController(repository);


    @Test
    void login() {
        ModelAndView tested = classUnderTest.login();

        assertEquals("login", tested.getViewName());
    }

    @Test
    void error() {
        ModelAndView tested = classUnderTest.error();

        assertEquals("403", tested.getViewName());
        assertEquals("You are not authorized for the requested data." ,
                tested.getModelMap().getAttribute("errorMsg"));
    }

    @Test
    void logout() {
        ModelAndView tested = classUnderTest.logout();
        assertEquals("logout", tested.getViewName());
    }
}