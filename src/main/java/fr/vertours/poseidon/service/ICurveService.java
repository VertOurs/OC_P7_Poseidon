package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.CurvePoint;
import fr.vertours.poseidon.dto.CurvePointDTO;

import java.util.List;

public interface ICurveService {

    List<CurvePoint> findAll();

    void save(CurvePointDTO dto);

    CurvePoint findId(Integer id);

    void updateId(Integer id, CurvePointDTO dto);

    void deleteId(Integer id);
}
