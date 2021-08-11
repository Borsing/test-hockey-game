package com.maplr.testhockeygame.service;

import com.maplr.testhockeygame.domain.Player;
import com.maplr.testhockeygame.domain.Team;
import com.maplr.testhockeygame.repository.PlayerRepository;
import com.maplr.testhockeygame.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public Team getTeamByYear(final Long year) {
        return teamRepository.findTeamByYear(year)
                .orElseThrow(() -> new ResourceNotFoundException("Team with the year " + year + " doesn't exist"));
    }

    public Player addPlayerInTeam(final Long yearOfTheTeam, final Player playerToAdd) {
        return teamRepository.findTeamByYear(yearOfTheTeam).map(team -> {
            playerToAdd.setTeam(team);
            return playerRepository.save(playerToAdd);
        }).orElseThrow(() -> new ResourceNotFoundException("Team with the year " + yearOfTheTeam + " doesn't exist"));
    }
}
