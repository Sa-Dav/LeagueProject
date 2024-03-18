package com.example.demo.service;

import com.example.demo.repository.RiotAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

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

    public static void parseObject(JSONObject json, String key) throws JSONException, IOException {
//        System.out.println(json.has(key));
        System.out.println("1-----------------------");
        System.out.println(json.get(key));

        JSONArray innerJSONA = (JSONArray) json.get(key);

        for (int i = 0; i < innerJSONA.length(); i++) {
            JSONObject jsonObject = (JSONObject) innerJSONA.get(i);
            System.out.println(jsonObject.get("championId"));
        }


        ObjectMapper objectMapper = new ObjectMapper();
//        String jj = json.get(key).toString();
//
//        Teams teams = objectMapper.readValue(jj, Teams.class);
//        System.out.println("2--------------------------------------------------------");
//        System.out.println(teams);
//        System.out.println("3--------------------------------------------------------");

//        Dragon dragon = objectMapper.readValue(json.get(key).toString(), Dragon.class);
//        dragon.toString();
        System.out.println("3--------------------------------------------------------");

//        List<Bans> bans = objectMapper.readValue(json.get(key).toString(), new TypeReference<List<Bans>>() {});
//        System.out.println(bans);
//        System.out.println("4--------------------------------------------------------");


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
                        if (!has) {
                            getKey(json.getJSONObject(nextKey), key);
                        }

                    } else if (json.get(nextKey) instanceof JSONArray) {
                        JSONArray jsonArray = json.getJSONArray(nextKey);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String jsonarrayString = jsonArray.get(i).toString();
                            JSONObject innerJSON = new JSONObject(jsonarrayString);

                            if (!has) {
                                getKey(innerJSON, key);
                            }
                        }
                    }

                } catch (Exception e) {
                    //TODO handle
                }
            }
        } else {
            parseObject(json, key);
        }
    }





    public void readFileByMatchId(String matchID) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new FileReader("E:\\lol_Project\\all_match\\" + matchID + ".txt"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        JSONObject jsonObject = new JSONObject(String.valueOf(sb));
//TODO Logika a statisztikához ban heylett
        getKey(jsonObject, "bans");
    }
}
