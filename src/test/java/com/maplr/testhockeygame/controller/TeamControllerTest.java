package com.maplr.testhockeygame.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplr.testhockeygame.dto.PlayerDto;
import com.maplr.testhockeygame.dto.TeamWithoutPlayersDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {

    @Autowired
    private MockMvc client;

    @ParameterizedTest
    @CsvSource({"2018", "2019"})
    void testGetTeamByYear(final Long year) throws Exception {
        // Setup the following body expectation
        var jsonExpected = Files.readString(Paths.get(this.getClass().getResource("getTeamByYear" + year + "BodyExpected.json").toURI()));

        // When I call my team
        client.perform(get("/api/team/{year}", year)
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())

                // Then I expect the answer to be 200 with the correct team
                .andExpect(status().is(200))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(jsonExpected, false));
    }

    @ParameterizedTest
    @MethodSource
    void testAddPlayerInTeam(final Long year, final PlayerDto playerToAddInTeam, final Integer expectedSize) throws Exception {
        // Given the player dto as json
        var playerToAddInTeamJson = new ObjectMapper().writeValueAsString(playerToAddInTeam);

        // When I want to create a player
        client.perform(post("/api/team/{year}", year)
                .contentType(APPLICATION_JSON)
                .content(playerToAddInTeamJson)
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())

                // Then I expect to get back my player as response with 201
                .andExpect(status().is(201))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(playerToAddInTeamJson, false));

        // When I call all the team
        var teamJson = client.perform(MockMvcRequestBuilders.get("/api/team/{year}", year)
                .accept(APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                // I find my player in the team
                .andExpect(jsonPath("$.players", Matchers.hasSize(expectedSize)))
                .andExpect(jsonPath("$.players[*].number", Matchers.hasItem(playerToAddInTeam.getNumber().intValue())));
    }

    static Stream<Arguments> testAddPlayerInTeam() {
        return Stream.of(
                arguments(2021L, new PlayerDto(88L, "Henry", "Carvil", "defenseman", true), 3),
                arguments(2021L, new PlayerDto(99L, "Thomas", "Nabil", "defenseman", false), 4)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testCreateTeam(final TeamWithoutPlayersDto teamWithoutPlayersDto) throws Exception {
        // Given my team as json
        var teamWithoutPlayersDtoJson = new ObjectMapper().writeValueAsString(teamWithoutPlayersDto);
        var year = teamWithoutPlayersDto.getYear();

        // When I want to create my team
        client.perform(post("/api/team")
                .contentType(APPLICATION_JSON)
                .content(teamWithoutPlayersDtoJson)
                .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())

                // Then I expect the answer to be correct
                .andExpect(status().is(201))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(teamWithoutPlayersDto.getId()))
                .andExpect(jsonPath("$.coach").value(teamWithoutPlayersDto.getCoach()))
                .andExpect(jsonPath("$.year").value(teamWithoutPlayersDto.getYear()))
                .andExpect(jsonPath("$.players").value(Matchers.hasSize(0)));


        // When I call my team created
        client.perform(MockMvcRequestBuilders.get("/api/team/{year}", year)
                .accept(APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                // I find my team
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(teamWithoutPlayersDto.getId()));
    }

    static Stream<Arguments> testCreateTeam() {
        return Stream.of(
                arguments(new TeamWithoutPlayersDto(1011L, "Eric Smith", 2022L)),
                arguments(new TeamWithoutPlayersDto(1213L, "Mike Toledo", 2023L))
        );
    }
}
