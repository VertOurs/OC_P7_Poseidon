package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.Rating;
import fr.vertours.poseidon.dto.RatingDTO;
import fr.vertours.poseidon.repository.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static fr.vertours.poseidon.converter.ConverterDTOToEntity.getRatingEntity;

@Service
@Slf4j
public class RatingServiceImpl implements IRatingService {

    private final RatingRepository repository;

    public RatingServiceImpl(RatingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Rating> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void save(RatingDTO dto) {
        Rating rating = getRatingEntity(dto);
        repository.save(rating);
        log.debug("Entity saved in DB : "+ rating.toString());
    }

    @Override
    public Rating findId(Integer id) {
        Rating rating = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid CurvePoint Id:" + id));
        return rating;
    }

    @Override
    @Transactional
    public void updateId(Integer id, RatingDTO dto) {
        Rating rating = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid CurvePoint Id:" + id));

        rating.setMoodysRating(dto.getMoodysRating());
        rating.setSandPRating(dto.getSandPRating());
        rating.setFitchRating(dto.getFitchRating());
        rating.setOrderNumber(dto.getOrderNumber());

        repository.save(rating);
        log.debug("Entity update in DB : "+ rating.toString());
    }

    @Override
    public void deleteId(Integer id) {
        repository.deleteById(id);
        log.debug("Entity deledted in DB");
    }
}
