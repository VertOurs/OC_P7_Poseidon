package fr.vertours.poseidon.repository;

import fr.vertours.poseidon.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatingRepository extends JpaRepository<Rating, Integer> {
}
