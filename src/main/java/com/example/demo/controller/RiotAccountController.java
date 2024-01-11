package com.example.demo.controller;

import com.example.demo.dto.SummonerDTO;
import com.example.demo.service.RiotAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/riotAccount")
@Slf4j
public class RiotAccountController {

    private final RiotAccountService riotAccountService;

    @Autowired
    public RiotAccountController(RiotAccountService riotAccountService) {
        this.riotAccountService = riotAccountService;
    }

    @GetMapping("/p={gameName}/t={tagLine}")
    public ResponseEntity<SummonerDTO> getUser(@PathVariable("gameName") String gameName, @PathVariable("tagLine") String tagLine) throws IOException {
        riotAccountService.findSummonerByNAMEAndTAG(gameName, tagLine);
        return null;
    }

    @GetMapping("p={gameName}/t={tagLine}/matchHistory")
    public ResponseEntity<SummonerDTO> getUserMatchHistory(@PathVariable("gameName") String gameName, @PathVariable("tagLine") String tagLine) throws IOException {
        riotAccountService.findMatchHistory(gameName, tagLine);
        return null;
    }
}
