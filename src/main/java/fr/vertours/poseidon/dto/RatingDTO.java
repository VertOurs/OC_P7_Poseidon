package fr.vertours.poseidon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {


    //private Integer id;

    @NotBlank(message = "moodysRating is mandatory")
    private String moodysRating;

    @NotBlank(message = "sandPRating is mandatory")
    private String sandPRating;

    @NotBlank(message = "fitchRating is mandatory")
    private String fitchRating;

    @NotNull(message = "orderNumber is mandatory")
    @Positive
    private Integer orderNumber;
}
