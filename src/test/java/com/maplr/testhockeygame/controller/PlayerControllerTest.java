package com.maplr.testhockeygame.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplr.testhockeygame.dto.PlayerDto;
import com.maplr.testhockeygame.dto.TeamDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {

    @Autowired
    private MockMvc client;

    @ParameterizedTest
    @CsvSource({"44, 2020, 200", "55, 2020, 200"})
    void testBecomeCaptainOfTeam(final Long playerNumber, final Long year, final Integer statusExpected) throws Exception {
        // When I make the call to set the playerNumber as captain
        client.perform(put("/api/player/captain/{playerNumber}", playerNumber)
                .accept(APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
        // Then I expect the player is the captain
                .andExpect(status().is(statusExpected))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.isCaptain").value(true));

        // When I call all the team
        var teamJson = client.perform(MockMvcRequestBuilders.get("/api/team/{year}", year)
                .accept(APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        // Then I expect the others players are not the captain
        var teamDto = new ObjectMapper().readValue(teamJson, TeamDto.class);

        var noOthersPlayersAreCaptain = teamDto.getPlayers().stream().filter(p -> !Objects.equals(p.getNumber(), playerNumber)).noneMatch(PlayerDto::getIsCaptain);
        Assertions.assertTrue(noOthersPlayersAreCaptain);

        // And there is a captain
        var playerWhoIsCaptain = teamDto.getPlayers().stream().filter(p -> Objects.equals(p.getNumber(), playerNumber)).findFirst();
        var thereIsACaptain = playerWhoIsCaptain.isPresent();
        Assertions.assertTrue(thereIsACaptain);

        // And the captain is the player modified
        var numberOfCaptain = playerWhoIsCaptain.get().getNumber();
        Assertions.assertEquals(playerNumber, numberOfCaptain);
    }
}
