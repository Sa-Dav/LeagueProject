package com.example.demo.service;

import com.example.demo.domain.LeagueGame;
import com.example.demo.domain.RiotAccount;
import com.example.demo.domain.Subscriber;
import com.example.demo.dto.SubscriberCommand;
import com.example.demo.dto.SubscriberDTO;
import com.example.demo.exceptionHandling.ThisAccountAlreadySubscribedException;
import com.example.demo.repository.SubscriberRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class SubscribeService {
    private static final String EMAIL_SUBJECT = "New game statistics";
    private static final String EMAIL_BODY = "New game detected! Here is your statistics:";

    private SubscriberRepository subscriberRepository;
    private RiotAccountService riotAccountService;
    private EmailSenderService emailSenderService;
    private LeagueGameService leagueGameService;
    private LolService lolService;


    @Value("${lol.apiKey}")
    private String lolApiKey;

    @Autowired
    public SubscribeService(SubscriberRepository subscriberRepository, RiotAccountService riotAccountService, EmailSenderService emailSenderService, LeagueGameService leagueGameService, LolService lolService) {
        this.subscriberRepository = subscriberRepository;
        this.riotAccountService = riotAccountService;
        this.emailSenderService = emailSenderService;
        this.leagueGameService = leagueGameService;
        this.lolService = lolService;
    }


    @Scheduled(fixedDelay = 30000)
    public void asd() throws IOException, JSONException, MessagingException {

        List<SubscriberDTO> subscriberDTO = subscriberRepository.findSubscriberNewGame();
        List<RiotAccount> listForSaveNewGame = new ArrayList<>();

        for (SubscriberDTO subscriberFromDTO : subscriberDTO) {
            String updatedLastMatch = riotAccountService.checkLastMatch(subscriberFromDTO.getPuuid());
            if (!updatedLastMatch.equals(subscriberFromDTO.getLastMatchId())) {

                RiotAccount currentRiotAccountInDB = subscriberRepository.findByPuuid(subscriberFromDTO.getPuuid());
                currentRiotAccountInDB.setLastMatchId(updatedLastMatch);
                currentRiotAccountInDB.setLastChange(LocalDateTime.now());

                String pureMatchId = updatedLastMatch.substring(2, updatedLastMatch.length() - 2);

                       LeagueGame leagueGame = leagueGameService.matchExist(pureMatchId);
                    String leagueGameBansS = leagueGame.getBans();
                       List<String> s = Arrays.asList(leagueGameBansS.split(","));
                       List<Integer> ints = new ArrayList<>();
                       if (s.size() > 1){
                           for (int i = 0; i < s.size(); i++) {
                               ints.add(Integer.parseInt(s.get(i)));
                           }
                       }



                System.out.println("--------------------------------------");
                System.out.println(ints);
                System.out.println("--------------------------------------");
                List<String> bannedChamps = new ArrayList<>();
                for (Integer i1:ints) {
                    ChampionByIDService championByIDService = new ChampionByIDService();
                    bannedChamps.add(championByIDService.championById(i1));
                }
                System.out.println("--------------------------------------");
                System.out.println(bannedChamps);
                System.out.println("--------------------------------------");
//                lolService.readFileByMatchId(pureMatchId, "bans");

                //TODO last game changes statistic
                emailSenderService.sendEmail2(subscriberFromDTO.getEmail(), EMAIL_SUBJECT, bannedChamps);

                listForSaveNewGame.add(currentRiotAccountInDB);
            }
        }
        riotAccountService.saveChangedSubcribers(listForSaveNewGame);
    }

    public void saveNewSubscriber(SubscriberCommand subscriberCommand) throws IOException {
        RiotAccount rito = riotAccountService.findSummonerByNAMEAndTAG(subscriberCommand.getGameName(), subscriberCommand.getTagLine());
        if (rito.getSubscriber() != null) {
            throw new ThisAccountAlreadySubscribedException(rito.getGameName());
        }

        Subscriber sub = subscriberRepository.findByEmail(subscriberCommand.getEmail());

        if (sub != null) {
            rito.setSubscriber(sub);
            riotAccountService.saveChangedRiotAccount(rito);
        } else {
            Subscriber subsc = new Subscriber();
            subsc.setEmail(subscriberCommand.getEmail());
            rito.setSubscriber(subsc);
            subscriberRepository.save(subsc);
            riotAccountService.saveChangedRiotAccount(rito);
        }
    }
}