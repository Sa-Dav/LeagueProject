package com.example.demo.controller;

import com.example.demo.service.LolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/asd")
@Slf4j
public class LolController {

    private final LolService lolService;

    @Autowired
    public LolController(LolService lolService) {
        this.lolService = lolService;
    }




}
