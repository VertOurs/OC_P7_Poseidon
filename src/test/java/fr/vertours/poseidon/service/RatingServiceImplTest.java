package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.Rating;
import fr.vertours.poseidon.dto.RatingDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.repository.RatingRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.vertours.poseidon.utils.FakeRating.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class RatingServiceImplTest {

    RatingRepository repository = mock(RatingRepository.class);

    RatingServiceImpl classUnderTest = new RatingServiceImpl(repository);

    @Test
    void findAll() {
        List<Rating> listMocked = getAll();
        when(repository.findAll()).thenReturn(listMocked);

        List<Rating> listTested = classUnderTest.findAll();

        verify(repository, times(1)).findAll();
        assertEquals(
                listMocked.get(0).getFitchRating(),
                listTested.get(0).getFitchRating()
        );
    }

    @Test
    void save() {
        RatingDTO dtoMocked = getDTO1();

        classUnderTest.save(dtoMocked);

        verify(repository, times(1)).save(any(Rating.class));
    }

    @Test
    void findId() {
        Rating userMocked = getEntity1();
        when(repository.findById(50)).thenReturn(java.util.Optional.of(userMocked));

        Rating userTested = classUnderTest.findId(50);

        verify(repository, times(1)).findById(50);
        assertEquals(
                userMocked.getFitchRating(),
                userTested.getFitchRating());
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
        Rating userMocked = getEntity1();
        RatingDTO dto = getDTO1();
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