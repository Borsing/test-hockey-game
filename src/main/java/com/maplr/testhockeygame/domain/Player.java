package com.maplr.testhockeygame.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Player {

    @Id
    @Column(nullable = false)
    private Long number;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Boolean isCaptain;

    @ManyToOne
    private Team team;

}
