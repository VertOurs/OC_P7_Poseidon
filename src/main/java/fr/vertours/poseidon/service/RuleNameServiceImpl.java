package fr.vertours.poseidon.service;

import fr.vertours.poseidon.domain.RuleName;
import fr.vertours.poseidon.dto.RuleNameDTO;
import fr.vertours.poseidon.exception.InvalidIDException;
import fr.vertours.poseidon.repository.RuleNameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static fr.vertours.poseidon.converter.ConverterDTOToEntity.getRuleNameEntity;

@Service
@Slf4j
public class RuleNameServiceImpl implements IRuleNameService {

    private final RuleNameRepository repository;

    public RuleNameServiceImpl(RuleNameRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RuleName> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void save(RuleNameDTO dto) {
        RuleName ruleName = getRuleNameEntity(dto);
        repository.save(ruleName);
        log.debug("Entity saved in DB : "+ ruleName.toString());

    }

    @Override
    public RuleName findId(Integer id) {
        RuleName ruleName = repository.findById(id).orElseThrow(
                () -> new InvalidIDException(id));
        return ruleName;
    }

    @Override
    @Transactional
    public void updateId(Integer id, RuleNameDTO dto) {
        RuleName ruleName = repository.findById(id).orElseThrow(
                () -> new InvalidIDException(id));
        ruleName.setName(dto.getName());
        ruleName.setDescription(dto.getDescription());
        ruleName.setJson(dto.getJson());
        ruleName.setTemplate(dto.getTemplate());
        ruleName.setSqlStr(dto.getSqlStr());
        ruleName.setSqlPart(dto.getSqlPart());

        repository.save(ruleName);
        log.debug("Entity update in DB : "+ ruleName.toString());
    }

    @Override
    public void deleteId(Integer id) {
        repository.deleteById(id);
        log.debug("Entity deledted in DB");
    }
}
