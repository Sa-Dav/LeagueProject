package com.example.demo.repository;

import com.example.demo.domain.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    @Query("SELECT s FROM Statistic s WHERE s.leagueGame.id = :id")
    List<Statistic> getAllById(@Param("id")Long id);

}
