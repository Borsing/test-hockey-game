package com.maplr.testhockeygame.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
