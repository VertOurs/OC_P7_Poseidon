package fr.vertours.poseidon.integration;

import fr.vertours.poseidon.domain.User;
import fr.vertours.poseidon.dto.UserDTO;
import fr.vertours.poseidon.repository.UserRepository;
import fr.vertours.poseidon.service.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;

import static fr.vertours.poseidon.utils.FakeUsers.getDTO1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@EnableTransactionManagement
public class UserServiceIT {
    
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    UserServiceImpl classUnderTest;

    private Integer id = null;


    @Test
    @Order(1)
    @Transactional
    void findAll() {
        User user = new User(
                "UserNameTEST",
                "PassTEST",
                "FullNameTEST",
                "ADMIN");
        repository.save(user);
        List<User> userList = classUnderTest.findAll();

        Optional<User> userExpected = userList.stream().filter(
                u -> u.getId().equals(1)).findFirst();

        assertEquals("FullNameTEST", userExpected.get().getFullname());

        id = user.getId();
    }

    @Test
    @Order(2)
    void updateId() {
        UserDTO dto = getDTO1();

        classUnderTest.updateId(id, dto);
        Optional<User> userExpected = repository.findById(id);

        assertEquals("1@", userExpected.get().getUsername());
    }

    @Test
    @Order(3)
    void deleteId() {
        classUnderTest.deleteId(id);
        User userExpected = null;

        Optional<User> optUser = repository.findById(id);
        if(optUser.isPresent()) {
            userExpected = optUser.get();
        }


        assertNull(userExpected);
    }
}
