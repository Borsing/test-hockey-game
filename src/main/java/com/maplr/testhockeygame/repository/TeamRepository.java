package com.maplr.testhockeygame.repository;

import com.maplr.testhockeygame.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
