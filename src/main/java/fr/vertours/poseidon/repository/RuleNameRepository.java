package fr.vertours.poseidon.repository;

import fr.vertours.poseidon.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
