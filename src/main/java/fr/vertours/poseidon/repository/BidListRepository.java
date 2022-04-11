package fr.vertours.poseidon.repository;

import fr.vertours.poseidon.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
