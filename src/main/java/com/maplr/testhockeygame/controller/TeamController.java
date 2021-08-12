package com.maplr.testhockeygame.controller;

import com.maplr.testhockeygame.dto.PlayerDto;
import com.maplr.testhockeygame.dto.TeamDto;
import com.maplr.testhockeygame.dto.TeamWithoutPlayersDto;
import com.maplr.testhockeygame.mapper.PlayerMapper;
import com.maplr.testhockeygame.mapper.TeamMapper;
import com.maplr.testhockeygame.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/team")
@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final PlayerMapper playerMapper;

    @GetMapping("/{year}")
    public TeamDto getTeamByYear(@PathVariable("year") final Long year) {
        var team = this.teamService.getTeamByYear(year);
        return this.teamMapper.toTeamDto(team);
    }

    @PostMapping("/{year}")
    public PlayerDto addPlayerInTeam(@PathVariable("year") final Long year, @Valid @RequestBody PlayerDto playerDto) {
        var playerToAdd = this.playerMapper.toPlayer(playerDto);
        var playerAdded = this.teamService.addPlayerInTeam(year, playerToAdd);
        return this.playerMapper.toPlayerDto(playerAdded);
    }

    @PostMapping
    public TeamDto createTeam(@Valid @RequestBody final TeamWithoutPlayersDto teamWithoutPlayersDto) {
        var teamToCreate = this.teamMapper.toTeam(teamWithoutPlayersDto);
        var teamCreated = this.teamService.createTeam(teamToCreate);
        return this.teamMapper.toTeamDto(teamCreated);
    }


}
