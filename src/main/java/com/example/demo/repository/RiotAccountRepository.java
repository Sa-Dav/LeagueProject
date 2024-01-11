package com.example.demo.repository;

import com.example.demo.domain.RiotAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RiotAccountRepository extends JpaRepository<RiotAccount, Long> {

    @Query("SELECT r.puuid FROM RiotAccount r WHERE r.gameName = :gameName AND r.tagLine = :tagLine")
    String findByGameNameAndTagLine(@Param("gameName") String gameName, @Param("tagLine") String tagLine);
}
