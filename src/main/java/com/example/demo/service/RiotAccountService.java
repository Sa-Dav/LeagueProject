package com.example.demo.service;

import com.example.demo.domain.RiotAccount;
import com.example.demo.exceptionHandling.RiotAccountNotFoundException;
import com.example.demo.repository.RiotAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
@Transactional
@Slf4j
public class RiotAccountService {

    private RiotAccountRepository riotAccountRepository;

    @Value("${lol.apiKey}")
    private String lolApiKey;

    @Autowired
    public RiotAccountService(RiotAccountRepository riotAccountRepository) {
        this.riotAccountRepository = riotAccountRepository;
    }

    public String findSummonerByNAMEAndTAG(String gameName, String tagLine) throws IOException {
        String alreadyExistMatch = riotAccountRepository.findByGameNameAndTagLine(gameName, tagLine);
        if (alreadyExistMatch != null) {
            log.info("This account already stored in database");
            return alreadyExistMatch;
        }

        URL url = new URL("https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine + "?api_key=" + lolApiKey);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            System.out.println(sb);
            ObjectMapper objectMapper = new ObjectMapper();

            RiotAccount riotAccount = objectMapper.readValue(String.valueOf(sb), RiotAccount.class);
            riotAccountRepository.save(riotAccount);
            return riotAccount.getPuuid();
        } else {
            throw new RiotAccountNotFoundException(gameName, tagLine);
        }
    }
}
