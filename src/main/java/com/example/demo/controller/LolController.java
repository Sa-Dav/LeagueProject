package com.example.demo.controller;

import com.example.demo.dto.SummonerDTO;
import com.example.demo.service.LolService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/asd22")
@Slf4j
public class LolController {

    private final LolService lolService;

    @Autowired
    public LolController(LolService lolService) {
        this.lolService = lolService;
    }




    @GetMapping("/p")
    public ResponseEntity<SummonerDTO> getUser() throws JSONException, IOException {
        lolService.readFileByMatchId("EUN1_3528597419");
        return null;
    }

}
