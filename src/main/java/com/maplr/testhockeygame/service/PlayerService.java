package com.maplr.testhockeygame.service;

import com.maplr.testhockeygame.domain.Player;
import com.maplr.testhockeygame.exception.NotFoundException;
import com.maplr.testhockeygame.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public Player becomeCaptainOfTeam(final Long playerNumber) {
        return playerRepository.findById(playerNumber)
                .map(newCaptain -> {
                    var playersOfTheTeam = newCaptain.getTeam().getPlayers();
                    playersOfTheTeam.forEach(playerInTeam -> playerInTeam.setIsCaptain(false));
                    newCaptain.setIsCaptain(true);
                    playerRepository.saveAll(playersOfTheTeam);
                    return newCaptain;
                }).orElseThrow(() -> new NotFoundException("Player with the number " + playerNumber + " doesn't exist."));
    }

}
