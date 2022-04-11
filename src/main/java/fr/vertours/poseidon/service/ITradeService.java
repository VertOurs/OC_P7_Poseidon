package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.CurvePoint;
import fr.vertours.poseidon.domain.Trade;
import fr.vertours.poseidon.dto.CurvePointDTO;
import fr.vertours.poseidon.dto.TradeDTO;

import java.util.List;

public interface ITradeService {

    List<Trade> findAll();

    void save(TradeDTO dto);

    Trade findId(Integer id);

    void updateId(Integer id, TradeDTO dto);

    void deleteId(Integer id);
}
