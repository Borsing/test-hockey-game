package com.maplr.testhockeygame.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Team {

    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String coach;

    @Column(nullable = false, unique = true)
    private Long year;

    @Column(nullable = false)
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<Player> players;


}
