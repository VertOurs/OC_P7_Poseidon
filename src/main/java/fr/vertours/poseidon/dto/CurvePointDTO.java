package fr.vertours.poseidon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurvePointDTO {

    @NotNull(message = "curveId is mandatory")
    private Integer curveId;

    @NotNull(message = "term is mandatory")
    private Double term;

    @NotNull(message = "value is mandatory")
    private Double value;
}
