package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.CurvePoint;
import fr.vertours.poseidon.dto.CurvePointDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.repository.CurvePointRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.vertours.poseidon.utils.FakeCurvePoint.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CurveServiceImplTest {

    CurvePointRepository repository = mock(CurvePointRepository.class);

    CurveServiceImpl classUnderTest = new CurveServiceImpl(repository);

    @Test
    void findAll() {
        List<CurvePoint> listMocked = getAll();
        when(repository.findAll()).thenReturn(listMocked);

        List<CurvePoint> listTested = classUnderTest.findAll();

        verify(repository, times(1)).findAll();
        assertEquals(
                listMocked.get(0).getTerm(),
                listTested.get(0).getTerm()
        );
    }

    @Test
    void save() {
        CurvePointDTO dtoMocked = getDTO1();

        classUnderTest.save(dtoMocked);

        verify(repository, times(1)).save(any(CurvePoint.class));
    }

    @Test
    void findId() {
        CurvePoint userMocked = getEntity1();
        when(repository.findById(50)).thenReturn(java.util.Optional.of(userMocked));

        CurvePoint userTested = classUnderTest.findId(50);

        verify(repository, times(1)).findById(50);
        assertEquals(
                userMocked.getTerm(),
                userTested.getTerm());
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
        CurvePoint userMocked = getEntity1();
        CurvePointDTO dto = getDTO1();
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