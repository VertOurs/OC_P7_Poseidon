package fr.vertours.poseidon.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BidList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;

    @NotNull
    private String account;

    @NotNull
    private String type;

    @NotNull
    private Double bidQuantity;


    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private Timestamp bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private Timestamp creationDate;
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;
}
