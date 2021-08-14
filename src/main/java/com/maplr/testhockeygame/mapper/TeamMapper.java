package com.maplr.testhockeygame.mapper;

import com.maplr.testhockeygame.domain.Player;
import com.maplr.testhockeygame.domain.Team;
import com.maplr.testhockeygame.dto.TeamDto;
import com.maplr.testhockeygame.dto.TeamWithoutPlayersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE, uses = PlayerMapper.class, componentModel = "spring")
public interface TeamMapper {

    TeamDto toTeamDto(final Team team);

    @Mapping(target = "players", source = "players")
    Team toTeam(final TeamWithoutPlayersDto teamWithoutPlayersDto, final List<Player> players);

    default Team toTeam(final TeamWithoutPlayersDto teamWithoutPlayersDto) {
        return this.toTeam(teamWithoutPlayersDto, Collections.emptyList());
    }
}
