package com.example.demo.service;

import com.example.demo.domain.LeagueGame;
import com.example.demo.exceptionHandling.MatchNotFoundException;
import com.example.demo.repository.LeagueGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
@Transactional
@Slf4j
public class LeagueGameService {

    private LeagueGameRepository matchRepository;

    @Value("${lol.apiKey}")
    private String lolApiKey;

    public LeagueGameService(LeagueGameRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public String matchExist(String matchId) throws IOException {
        String alreadyExistMatch = matchRepository.findByMatchId(matchId);
        if (alreadyExistMatch != null) {
            log.info("This match already stored in database");
            return alreadyExistMatch;
        }


        URL url = new URL("https://europe.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + lolApiKey);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("Response CODE: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(connection.getInputStream());
            String pathToFile = "E:\\lol_Project\\all_match\\" + matchId + ".txt";

            BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile));
            while (scanner.hasNext()) {
                writer.write(scanner.nextLine());
            }
            writer.close();

            matchRepository.save(new LeagueGame(matchId, pathToFile));
            log.info("This match stored to database : " + matchId);
            return pathToFile;
        } else {
            throw new MatchNotFoundException(matchId);
        }
    }
}