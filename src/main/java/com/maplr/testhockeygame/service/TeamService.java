package com.maplr.testhockeygame.service;

import com.maplr.testhockeygame.domain.Player;
import com.maplr.testhockeygame.domain.Team;
import com.maplr.testhockeygame.exception.BadRequestException;
import com.maplr.testhockeygame.exception.NotFoundException;
import com.maplr.testhockeygame.repository.PlayerRepository;
import com.maplr.testhockeygame.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final PlayerService playerService;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public Team getTeamByYear(final Long year) {
        return teamRepository.findTeamByYear(year)
                .orElseThrow(() -> new NotFoundException("Team with the year " + year + " doesn't exist"));
    }

    public Player addPlayerInTeam(final Long yearOfTheTeam, final Player playerToAdd) {
        return teamRepository.findTeamByYear(yearOfTheTeam).map(team -> {
            playerToAdd.setTeam(team);
            var playerAdded = playerRepository.save(playerToAdd);
            if (playerToAdd.getIsCaptain()) {
                return playerService.becomeCaptainOfTeam(playerAdded.getNumber());
            }
            return playerAdded;
        }).orElseThrow(() -> new NotFoundException("Team with the year " + yearOfTheTeam + " doesn't exist"));
    }

    public Team createTeam(final Team teamToCreate) {
        teamRepository.findTeamByIdOrYear(teamToCreate.getId(), teamToCreate.getYear()).ifPresent(teamExisted -> {
            if (Objects.equals(teamExisted.getYear(), teamToCreate.getYear()))
                throw new BadRequestException("Team for the year " + teamToCreate.getYear() + " already exists with id " + teamExisted.getId());

            if (Objects.equals(teamExisted.getId(), teamToCreate.getId()))
                throw new BadRequestException("Team for the id " + teamToCreate.getId() + " already exists");
        });
        return teamRepository.save(teamToCreate);
    }
}
