package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.BidList;
import fr.vertours.poseidon.dto.BidListDTO;

import java.util.List;

public interface IBidListService {

    List<BidList> findAll();

    void save(BidListDTO dto);

    BidList findId(Integer id);

    void updateId(Integer id, BidListDTO dto);

    void deleteId(Integer id);
}
