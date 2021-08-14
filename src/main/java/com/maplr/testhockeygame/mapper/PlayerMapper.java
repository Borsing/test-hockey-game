package com.maplr.testhockeygame.mapper;

import com.maplr.testhockeygame.domain.Player;
import com.maplr.testhockeygame.dto.PlayerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(nullValuePropertyMappingStrategy = IGNORE, componentModel = "spring")
public interface PlayerMapper {

    PlayerDto toPlayerDto(final Player player);

    @Mapping(target = "team", ignore = true)
    Player toPlayer(final PlayerDto playerDto);
}
