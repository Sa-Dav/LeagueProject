package com.example.demo.repository;

import com.example.demo.domain.LeagueGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface LeagueGameRepository extends JpaRepository<LeagueGame, Long> {
    @Query("SELECT l FROM LeagueGame l WHERE l.matchId = :matchId ")
    LeagueGame findByMatchId(@Param("matchId") String matchId);
}
