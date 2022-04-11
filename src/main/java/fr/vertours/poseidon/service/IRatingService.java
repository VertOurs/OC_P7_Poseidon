package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.CurvePoint;
import fr.vertours.poseidon.domain.Rating;
import fr.vertours.poseidon.dto.CurvePointDTO;
import fr.vertours.poseidon.dto.RatingDTO;

import java.util.List;

public interface IRatingService {

    List<Rating> findAll();


    void save(RatingDTO dto);

    Rating findId(Integer id);

    void updateId(Integer id, RatingDTO dto);

    void deleteId(Integer id);
}
