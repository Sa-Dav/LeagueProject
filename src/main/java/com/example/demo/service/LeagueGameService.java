package com.example.demo.service;

import com.example.demo.domain.LeagueGame;
import com.example.demo.exceptionHandling.MatchNotFoundException;
import com.example.demo.repository.LeagueGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

@Service
@Transactional
@Slf4j
public class LeagueGameService {

    private LolService lolService;
    private LeagueGameRepository matchRepository;

    @Value("${lol.apiKey}")
    private String lolApiKey;
    private static StringBuilder helper = new StringBuilder();
    @Autowired
    public LeagueGameService(LolService lolService, LeagueGameRepository matchRepository) {
        this.lolService = lolService;
        this.matchRepository = matchRepository;
    }



    public LeagueGame matchExist(String matchId) throws IOException, JSONException {
        LeagueGame alreadyExistMatch = matchRepository.findByMatchId(matchId);
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

            readFileByMatchId(matchId, "bans");

//          LeagueGame savedLeagueGame =
            LeagueGame leagueGame = matchRepository.save(new LeagueGame(matchId, pathToFile, LocalDateTime.now(), helper.toString()));
            helper = new StringBuilder();

            log.info("This match stored to database : " + matchId);
            return leagueGame;
        } else {
            throw new MatchNotFoundException(matchId);
        }
    }

    public static void getKey(JSONObject json, String key) throws JSONException, IOException {

        boolean has = json.has(key);
        Iterator<?> keys;
        String nextKey;

        if (!has) {
            keys = json.keys();
            while (keys.hasNext()) {
                nextKey = (String) keys.next();
                try {
                    if (json.get(nextKey) instanceof JSONObject) {
                        getKey(json.getJSONObject(nextKey), key);


                    } else if (json.get(nextKey) instanceof JSONArray) {
                        JSONArray jsonArray = json.getJSONArray(nextKey);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String jsonarrayString = jsonArray.get(i).toString();
                            JSONObject innerJSON = new JSONObject(jsonarrayString);
                            getKey(innerJSON, key);
                        }
                    }

                } catch (Exception e) {
                    //TODO handle
                }
            }
        } else {

            if (key.equals("bans")) {
                parseObjectBans(json, key);
            } else {
//                parseObjectStats(json, key);
            }
        }

    }

    public void readFileByMatchId(String matchID, String key) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new FileReader("E:\\lol_Project\\all_match\\" + matchID + ".txt"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        JSONObject jsonObject = new JSONObject(String.valueOf(sb));
//TODO Logika a statisztikához ban heylett
        getKey(jsonObject, key);
    }

    public static void parseObjectBans(JSONObject json, String key) throws JSONException, IOException {
//        System.out.println(json.has(key));

        JSONArray innerJSONA = (JSONArray) json.get(key);

        for (int i = 0; i < innerJSONA.length(); i++) {
            JSONObject jsonObject = (JSONObject) innerJSONA.get(i);
            helper.append(jsonObject.get("championId") + ",");

        }
    }
}