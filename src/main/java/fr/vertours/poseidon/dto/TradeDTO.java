package fr.vertours.poseidon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TradeDTO {

//    private Integer id;

    @NotBlank(message = "account is mandatory")
    private String account;

    @NotBlank(message = "type is mandatory")
    private String type;

    @NotNull(message = "buyQuantity is mandatory")
    private Double buyQuantity;
}
