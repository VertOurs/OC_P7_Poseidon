package fr.vertours.poseidon.utils;

import fr.vertours.poseidon.domain.RuleName;
import fr.vertours.poseidon.dto.RuleNameDTO;

import java.util.ArrayList;
import java.util.List;

public class FakeRuleName {

    public static RuleName getEntity1() {
        RuleName ruleName = new RuleName();
        ruleName.setName("Name");
        ruleName.setDescription("Desc");
        ruleName.setJson("Json");
        ruleName.setSqlPart("SqlPart");
        ruleName.setSqlStr("SqlStr");
        ruleName.setTemplate("Template");
        return ruleName;
    }
    public static RuleNameDTO getDTO1() {
        RuleNameDTO dto = new RuleNameDTO();
        dto.setName("Name");
        dto.setDescription("Desc");
        dto.setJson("Json");
        dto.setSqlPart("SqlPart");
        dto.setSqlStr("SqlStr");
        dto.setTemplate("Template");
        return dto;
    }

    public static List<RuleName> getAll() {
        List<RuleName> ruleNames = new ArrayList<>();
        ruleNames.add(getEntity1());
        return ruleNames;
    }
}
