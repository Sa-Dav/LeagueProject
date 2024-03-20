package com.example.demo.controller;

import com.example.demo.dto.SummonerDTO;
import com.example.demo.service.EmailSenderService;
import com.example.demo.service.LolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/asd22")
@Slf4j
public class LolController {

    private final LolService lolService;

    private EmailSenderService emailSenderService;

    @Autowired
    public LolController(LolService lolService, EmailSenderService emailSenderService) {
        this.lolService = lolService;
        this.emailSenderService = emailSenderService;
    }




//    @GetMapping("/p")
//    public ResponseEntity<SummonerDTO> getUser() throws JSONException, IOException {
//        lolService.readFileByMatchId("EUN1_3528597419", "bans");
//        return null;
//    }

//    @GetMapping("/testMail")
//    public ResponseEntity<SummonerDTO> getGet() throws MessagingException, IOException {
//        emailSenderService.sendEmailRanked("fundraiser20230824@gmail.com", "HI", List.of("AurelionSol","Darius","MasterYi","Diana","Zed","Zac","Malphite","Yuumi","Jinx","Vayne"));
//        return null;
//    }

}
