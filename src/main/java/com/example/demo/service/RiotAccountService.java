package com.example.demo.service;

import com.example.demo.domain.RiotAccount;
import com.example.demo.exceptionHandling.MatchHistoryNotFoundException;
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
import java.util.List;
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



    public RiotAccount findSummonerByNAMEAndTAG(String gameName, String tagLine) throws IOException {
        RiotAccount alreadyExistMatch = riotAccountRepository.findByGameNameAndTagLine(gameName, tagLine);
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
            riotAccount.setLastMatchId(checkLastMatch(riotAccount.getPuuid()));

            riotAccountRepository.save(riotAccount);
            return riotAccount;
        } else {
            throw new RiotAccountNotFoundException(gameName, tagLine);
        }
    }

    public void findMatchHistory(String gameName, String tagLine) throws IOException {
        RiotAccount riotAccount = findSummonerByNAMEAndTAG(gameName, tagLine);
        String riotAccountPuuid = riotAccount.getPuuid();

        URL url = new URL("https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/" + riotAccountPuuid + "/ids?start=0&count=" + "20" + "&api_key=" + lolApiKey);

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
        } else {
            throw new MatchHistoryNotFoundException(gameName, tagLine);
        }
    }

    public String checkLastMatch(String riotAccountPuuid) throws IOException {

        URL url = new URL("https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/" + riotAccountPuuid + "/ids?start=0&count=" + "1" + "&api_key=" + lolApiKey);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            return sb.toString();
        } else {
            throw new MatchHistoryNotFoundException(riotAccountPuuid, riotAccountPuuid);
        }
    }

    public void saveChangedSubcribers(List<RiotAccount> listForSaveNewGame) {
        riotAccountRepository.saveAll(listForSaveNewGame);
    }

    public void saveChangedRiotAccount(RiotAccount riotAccount) {
        riotAccountRepository.save(riotAccount);

    }
}
