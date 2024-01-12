package com.example.demo.controller;

import com.example.demo.dto.SubscriberCommand;
import com.example.demo.service.SubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/subscriber")
@Slf4j
public class SubscriberController {

    private SubscribeService subscribeService;

    @Autowired
    public SubscriberController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    @PostMapping("/subscribe")
    public void subscribeWithAccountAndEmail(@RequestBody SubscriberCommand subscriberCommand) throws IOException {
        subscribeService.saveNewSubscriber(subscriberCommand);
    }
}
