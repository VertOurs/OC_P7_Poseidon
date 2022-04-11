package fr.vertours.poseidon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RuleNameDTO {

    //private Integer id;

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotBlank(message = "description is mandatory")
    private String description;

    @NotBlank(message = "json is mandatory")
    private String json;

    @NotBlank(message = "template is mandatory")
    private String template;

    @NotBlank(message = "sqlStr is mandatory")
    private String sqlStr;

    @NotBlank(message = "sqlPart is mandatory")
    private String sqlPart;
}
