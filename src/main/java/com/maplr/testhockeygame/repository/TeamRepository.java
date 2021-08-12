package com.maplr.testhockeygame.repository;

import com.maplr.testhockeygame.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findTeamByYear(final Long year);

    Optional<Team> findTeamByIdOrYear(final Long id, final Long year);

}
