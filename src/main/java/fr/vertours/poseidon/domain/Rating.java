package fr.vertours.poseidon.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Rating {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String moodysRating;

    @NotNull
    private String sandPRating;

    @NotNull
    private String fitchRating;

    @NotNull
    private Integer orderNumber;
}
