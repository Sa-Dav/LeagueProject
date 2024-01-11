package com.example.demo.controller;

import com.example.demo.dto.SummonerDTO;
import com.example.demo.service.LeagueGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/leagueGame")
@Slf4j
public class LeagueGameController {

    private LeagueGameService leagueGameService;

    public LeagueGameController(LeagueGameService leagueGameService) {
        this.leagueGameService = leagueGameService;
    }

    @GetMapping("/m={matchID}")
    public ResponseEntity<SummonerDTO> getMatch(@PathVariable("matchID") String matchID) throws IOException {
        System.out.println(leagueGameService.matchExist(matchID));
        return null;
    }
}
