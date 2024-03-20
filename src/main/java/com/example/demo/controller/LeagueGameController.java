package com.example.demo.controller;

import com.example.demo.dto.SummonerDTO;
import com.example.demo.service.EmailSenderService;
import com.example.demo.service.LeagueGameService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
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

    private EmailSenderService emailSenderService;

    public LeagueGameController(LeagueGameService leagueGameService, EmailSenderService emailSenderService) {
        this.leagueGameService = leagueGameService;
        this.emailSenderService = emailSenderService;
    }

    @GetMapping("/m={matchID}")
    public ResponseEntity<SummonerDTO> getMatch(@PathVariable("matchID") String matchID) throws IOException, JSONException {
        System.out.println(leagueGameService.matchExist(matchID));
        return null;
    }

    @GetMapping("/jsonT")
    public ResponseEntity<SummonerDTO> jsonT() throws IOException, JSONException {
        leagueGameService.matchExist("EUN1_3547109679");
        return null;
    }

//    @GetMapping("/emailT")
//    public ResponseEntity<SummonerDTO> emailT() throws IOException, JSONException, MessagingException {
//        emailSenderService.sendEmailRanked("winderlox@gmail.com","asd", List.of("Darius","Zoe","Zac","Annie","Brand","Gnar","Kled","Elise","Jinx","Vayne"));
//        return null;
//    }
}
