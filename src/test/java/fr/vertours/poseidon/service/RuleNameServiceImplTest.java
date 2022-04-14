package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.RuleName;
import fr.vertours.poseidon.dto.RuleNameDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.repository.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


import static fr.vertours.poseidon.utils.FakeRuleName.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class RuleNameServiceImplTest {

    RuleNameRepository repository = mock(RuleNameRepository.class);

    RuleNameServiceImpl classUnderTest = new RuleNameServiceImpl(repository);

    @Test
    void findAll() {
        List<RuleName> listMocked = getAll();
        when(repository.findAll()).thenReturn(listMocked);

        List<RuleName> listTested = classUnderTest.findAll();

        verify(repository, times(1)).findAll();
        assertEquals(
                listMocked.get(0).getDescription(),
                listTested.get(0).getDescription()
        );
    }

    @Test
    void save() {
        RuleNameDTO dtoMocked = getDTO1();

        classUnderTest.save(dtoMocked);

        verify(repository, times(1)).save(any(RuleName.class));
    }

    @Test
    void findId() {
        RuleName userMocked = getEntity1();
        when(repository.findById(50)).thenReturn(java.util.Optional.of(userMocked));

        RuleName userTested = classUnderTest.findId(50);

        verify(repository, times(1)).findById(50);
        assertEquals(
                userMocked.getDescription(),
                userTested.getDescription());
    }

    @Test
    void findIdException() {
        when(repository.findById(50)).thenThrow(InvalidIDException.class);

        Exception exceptionTested =
                assertThrows(InvalidIDException.class,
                        () -> classUnderTest.findId(50));

        assertTrue(exceptionTested.getMessage().contains("this Id :"));
    }

    @Test
    void updateId() {
        RuleName userMocked = getEntity1();
        RuleNameDTO dto = getDTO1();
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