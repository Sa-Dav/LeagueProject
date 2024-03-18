package com.example.demo.service;

import com.example.demo.repository.RiotAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class LolService {

    private RiotAccountRepository riotAccountRepository;

    @Value("${lol.apiKey}")
    private String lolApiKey;

    @Autowired
    public LolService(RiotAccountRepository riotAccountRepository) {
        this.riotAccountRepository = riotAccountRepository;
    }













}
