package fr.vertours.poseidon.service;


import fr.vertours.poseidon.domain.RuleName;
import fr.vertours.poseidon.dto.RuleNameDTO;

import java.util.List;

public interface IRuleNameService {

    List<RuleName> findAll();

    void save(RuleNameDTO dto);

    RuleName findId(Integer id);

    void updateId(Integer id, RuleNameDTO dto);

    void deleteId(Integer id);
}
