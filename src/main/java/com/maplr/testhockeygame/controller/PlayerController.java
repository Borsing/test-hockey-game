package com.maplr.testhockeygame.controller;

import com.maplr.testhockeygame.dto.PlayerDto;
import com.maplr.testhockeygame.mapper.PlayerMapper;
import com.maplr.testhockeygame.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/player")
@Api(tags = "Player Service")
@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    @PutMapping("/captain/{number}")
    @ApiOperation("Set a player as new captain of his team")
    public PlayerDto becomeCaptainOfTeam(@PathVariable("number") final Long number) {
        var newCaptain = playerService.becomeCaptainOfTeam(number);
        return playerMapper.toPlayerDto(newCaptain);
    }
}
