package com.maplr.testhockeygame.repository;

import com.maplr.testhockeygame.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
