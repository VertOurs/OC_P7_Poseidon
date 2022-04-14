package fr.vertours.poseidon.utils;

import fr.vertours.poseidon.domain.Rating;
import fr.vertours.poseidon.dto.RatingDTO;


import java.util.ArrayList;
import java.util.List;

public class FakeRating {

    public static Rating getEntity1() {
        Rating rating = new Rating();
        rating.setFitchRating("Fitch");
        rating.setMoodysRating("Moody");
        rating.setOrderNumber(1);
        rating.setSandPRating("Sand");
        return rating;
    }

    public static RatingDTO getDTO1() {
        RatingDTO dto = new RatingDTO();
        dto.setFitchRating("Fitch");
        dto.setMoodysRating("Moody");
        dto.setOrderNumber(1);
        dto.setSandPRating("Sand");
        return dto;
    }

    public static List<Rating> getAll() {
        List<Rating> ratings = new ArrayList<>();
        ratings.add(getEntity1());
        return ratings;
    }
}
