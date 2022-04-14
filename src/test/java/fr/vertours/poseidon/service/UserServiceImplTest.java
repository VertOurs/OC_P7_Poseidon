package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.User;
import fr.vertours.poseidon.dto.UserDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static fr.vertours.poseidon.utils.FakeUsers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    UserRepository repository = mock(UserRepository.class);
    BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    UserServiceImpl classUnderTest = new UserServiceImpl(repository, encoder);

    @Test
    void initAdminUser() {
        when(repository.findByUsername("Admin@Poseidon.com")).thenReturn(null);

        classUnderTest.initAdminUser();

        verify(repository, times(1))
                .findByUsername("Admin@Poseidon.com");
        verify(repository, times(1)).save(any(User.class));
    }


    @Test
    void findAll() {
        List<User> listMocked = getAll();
        when(repository.findAll()).thenReturn(listMocked);

        List<User> listTested = classUnderTest.findAll();

        verify(repository, times(1)).findAll();
        assertEquals(
                listMocked.get(0).getUsername(),
                listTested.get(0).getUsername()
        );
    }

    @Test
    void save() {
        UserDTO dtoMocked = getDTO1();

        classUnderTest.save(dtoMocked);

        verify(repository, times(1)).save(any(User.class));

    }

    @Test
    void findId() {
        User userMocked = getEntity1();
        when(repository.findById(50)).thenReturn(java.util.Optional.of(userMocked));

        User userTested = classUnderTest.findId(50);

        verify(repository, times(1)).findById(50);
        assertEquals(
                userMocked.getFullname(),
                userTested.getFullname());
    }

    @Test
    void findIdException() {
        when(repository.findById(50)).thenThrow(InvalidIDException.class);

        Exception exceptionTested =
                assertThrows(InvalidIDException.class,
                        () -> classUnderTest.findId(50));
        System.out.println(exceptionTested.getMessage());
        assertTrue(exceptionTested.getMessage().contains("this Id :"));
    }

    @Test
    void updateId() {
        User userMocked = getEntity1();
        UserDTO dto = getDTO1();
        when(repository.findById(50)).thenReturn(java.util.Optional.of(userMocked));

        classUnderTest.updateId(50, dto);

        verify(repository, times(1)).findById(50);
        verify(repository, times(1)).save(userMocked);
    }

    @Test
    void deleteId() {
        classUnderTest.deleteId(50);
        verify(repository, times(1)).deleteById(50);
    }
}