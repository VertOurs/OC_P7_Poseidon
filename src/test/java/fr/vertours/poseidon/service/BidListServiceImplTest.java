package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.BidList;
import fr.vertours.poseidon.dto.BidListDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.repository.BidListRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.vertours.poseidon.utils.FakeBidList.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class BidListServiceImplTest {

    BidListRepository repository = mock(BidListRepository.class);

    BidListServiceImpl classUnderTest = new BidListServiceImpl(repository);

    @Test
    void findAll() {
        List<BidList> listMocked = getAll();
        when(repository.findAll()).thenReturn(listMocked);

        List<BidList> listTested = classUnderTest.findAll();

        verify(repository, times(1)).findAll();
        assertEquals(
                listMocked.get(0).getAccount(),
                listTested.get(0).getAccount()
        );
    }

    @Test
    void save() {
        BidListDTO dtoMocked = getDTO1();

        classUnderTest.save(dtoMocked);

        verify(repository, times(1)).save(any(BidList.class));
    }

    @Test
    void findId() {
        BidList userMocked = getEntity1();
        when(repository.findById(50)).thenReturn(java.util.Optional.of(userMocked));

        BidList userTested = classUnderTest.findId(50);

        verify(repository, times(1)).findById(50);
        assertEquals(
                userMocked.getAccount(),
                userTested.getAccount());
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
        BidList userMocked = getEntity1();
        BidListDTO dto = getDTO1();
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