package com.maplr.testhockeygame.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class TeamDto {

    @NotNull
    private Long id;

    @NotNull
    @NotBlank
    private String coach;

    @NotNull
    @Positive
    private Long year;

    @NotNull
    private List<PlayerDto> players;
}
