package com.maplr.testhockeygame.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class TeamWithoutPlayersDto {

    @NotNull
    private Long id;

    @NotNull
    @NotBlank
    private String coach;

    @Positive
    @NotNull
    private Long year;

}
