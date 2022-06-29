package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.Trade;
import fr.vertours.poseidon.dto.TradeDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static fr.vertours.poseidon.converter.ConverterDTOToEntity.getTradeEntity;

@Service
@Slf4j
public class TradeServiceImpl implements ITradeService {

    private final TradeRepository repository;

    public TradeServiceImpl(TradeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Trade> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void save(TradeDTO dto) {
        Trade trade = getTradeEntity(dto);
        repository.save(trade);
        log.debug("Entity saved in DB : "+ trade.toString());
    }
    @Override
    public Trade findId(Integer id) {
        Trade trade = repository.findById(id).orElseThrow(
                () -> new InvalidIDException(id));
        return trade;
    }
    @Override
    public void updateId(Integer id, TradeDTO dto) {
        Trade trade = repository.findById(id).orElseThrow(
                () -> new InvalidIDException(id));
        trade.setAccount(dto.getAccount());
        trade.setType(dto.getType());
        trade.setBuyQuantity(dto.getBuyQuantity());
        repository.save(trade);
        log.debug("Entity update in DB : "+ trade.toString());
    }
    @Override
    public void deleteId(Integer id) {
        repository.deleteById(id);
        log.debug("Entity deledted in DB");
    }
}
