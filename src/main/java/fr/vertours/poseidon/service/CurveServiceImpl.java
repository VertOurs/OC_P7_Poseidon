package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.CurvePoint;
import fr.vertours.poseidon.dto.CurvePointDTO;
import fr.vertours.poseidon.repository.CurvePointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static fr.vertours.poseidon.converter.ConverterDTOToEntity.getCurvePointEntity;

@Service
@Slf4j
public class CurveServiceImpl implements ICurveService {

    private final CurvePointRepository repository;

    public CurveServiceImpl(CurvePointRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<CurvePoint> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void save(CurvePointDTO dto) {
        CurvePoint curvePoint = getCurvePointEntity(dto);
        repository.save(curvePoint);
        log.debug("Entity saved in DB : "+ curvePoint.toString());
    }

    @Override
    public CurvePoint findId(Integer id) {
        CurvePoint curvePoint = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid CurvePoint Id:" + id));
        return curvePoint;
    }

    @Override
    @Transactional
    public void updateId(Integer id, CurvePointDTO dto) {
        CurvePoint curvePoint = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid CurvePoint Id:" + id));

        curvePoint.setCurveId(dto.getCurveId());
        curvePoint.setTerm(dto.getTerm());
        curvePoint.setValue(dto.getValue());
        repository.save(curvePoint);
        log.debug("Entity update in DB : "+ curvePoint.toString());
    }

    @Override
    public void deleteId(Integer id) {
        repository.deleteById(id);
        log.debug("Entity deledted in DB");
    }

}
