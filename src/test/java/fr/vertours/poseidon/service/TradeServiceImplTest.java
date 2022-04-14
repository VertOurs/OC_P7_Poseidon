package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.Trade;


import fr.vertours.poseidon.dto.TradeDTO;

import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.repository.TradeRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.vertours.poseidon.utils.FakeTrade.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class TradeServiceImplTest {

    TradeRepository repository = mock(TradeRepository.class);

    TradeServiceImpl classUnderTest = new TradeServiceImpl(repository);

    @Test
    void findAll() {
        List<Trade> listMocked = getAll();
        when(repository.findAll()).thenReturn(listMocked);

        List<Trade> listTested = classUnderTest.findAll();

        verify(repository, times(1)).findAll();
        assertEquals(
                listMocked.get(0).getAccount(),
                listTested.get(0).getAccount()
        );
    }

    @Test
    void save() {
        TradeDTO dtoMocked = getDTO1();

        classUnderTest.save(dtoMocked);

        verify(repository, times(1)).save(any(Trade.class));
    }

    @Test
    void findId() {
        Trade userMocked = getEntity1();
        when(repository.findById(50)).thenReturn(java.util.Optional.of(userMocked));

        Trade userTested = classUnderTest.findId(50);

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
        Trade userMocked = getEntity1();
        TradeDTO dto = getDTO1();
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