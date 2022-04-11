package fr.vertours.poseidon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;


import javax.validation.constraints.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BidListDTO {

    private Integer id;

    @NotBlank(message = "account is mandatory")
    private String account;

    @NotBlank(message = "type is mandatory")
    private String type;

    @NotNull(message = "bidQuantity is mandatory")
    private Double bidQuantity;
}
