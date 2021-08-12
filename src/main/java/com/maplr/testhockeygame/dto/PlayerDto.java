package com.maplr.testhockeygame.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PlayerDto {

    @NotNull
    @Positive
    private Long number;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String position;

    @NotNull
    private Boolean isCaptain;
}
