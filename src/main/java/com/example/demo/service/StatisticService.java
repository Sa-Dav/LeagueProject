package com.example.demo.service;

import com.example.demo.domain.Statistic;
import com.example.demo.repository.StatisticRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class StatisticService {

    private StatisticRepository statisticRepository;

    @Autowired
    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public Statistic saveStatistic (Statistic statistic){
        return statisticRepository.save(statistic);
    }

    public List<Statistic> getStatisticFromLeagueGame(Long id) {
        return statisticRepository.getAllById(id);
    }
}
