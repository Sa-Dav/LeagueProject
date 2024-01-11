package com.example.demo.service;

import com.example.demo.domain.Subscriber;
import com.example.demo.dto.SubscriberDTO;
import com.example.demo.exceptionHandling.MatchHistoryNotFoundException;
import com.example.demo.repository.SubscriberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Component
@Slf4j
public class SubscribeAccounts {

    private SubscriberRepository subscriberRepository;

    @Value("${lol.apiKey}")
    private String lolApiKey;

    @Autowired
    public SubscribeAccounts(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
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

    @Scheduled(fixedDelay = 30000)
    public void asd() throws IOException {

        List<SubscriberDTO> subscriberDTO = subscriberRepository.findSubscriberNewGame();

        for (SubscriberDTO subscriber:subscriberDTO) {
            String currentSubLastMatchId = checkLastMatch(subscriber.getPuuid());
            if (!currentSubLastMatchId.equals(subscriber.getLastMatchId())){
                Subscriber currentSub = subscriberRepository.findByEmail(subscriber.getEmail());
                currentSub.setLastMatchId(currentSubLastMatchId);
                subscriberRepository.save(currentSub);

            }

        }

    }
}
