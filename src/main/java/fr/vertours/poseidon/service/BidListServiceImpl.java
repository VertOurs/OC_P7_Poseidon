package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.BidList;
import fr.vertours.poseidon.dto.BidListDTO;
import fr.vertours.poseidon.repository.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static fr.vertours.poseidon.converter.ConverterDTOToEntity.getBidListEntity;

@Service
@Slf4j
public class BidListServiceImpl implements IBidListService{

    private final BidListRepository repository;

    public BidListServiceImpl(BidListRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BidList> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void save(BidListDTO dto) {
        BidList bidList = getBidListEntity(dto);
        repository.save(bidList);
        log.debug("Entity saved in DB : "+ bidList);
    }

    @Override
    public BidList findId(Integer id) {
        BidList bidList = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid Id:" + id));

        return bidList;
    }

    @Override
    @Transactional
    public void updateId(Integer id, BidListDTO dto) {
        BidList bidList = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid Id:" + id));

           bidList.setAccount(dto.getAccount());
           bidList.setType(dto.getType());
           bidList.setBidQuantity(dto.getBidQuantity());

        repository.save(bidList);
        log.debug("Entity update in DB : "+ bidList.toString());
    }

    @Override
    public void deleteId(Integer id) {
        repository.deleteById(id);
        log.debug("Entity deleted in DB");
    }
}
