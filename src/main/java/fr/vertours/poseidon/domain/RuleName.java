package fr.vertours.poseidon.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rulename")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String json;

    @NotNull
    private String template;

    @NotNull
    private String sqlStr;

    @NotNull
    private String sqlPart;
}
