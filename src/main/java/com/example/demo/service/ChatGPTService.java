package com.example.demo.service;

import com.example.demo.dto.ChatGPTRequest;
import com.example.demo.dto.ChatGPTResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@Slf4j
public class ChatGPTService {
    @Value("${chatGPT.model}")
    private String model;

    @Value("${chatGPT.url}")
    private String url;

    @Autowired
    private RestTemplate template;

    public void asd(String prompt) {

        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse = template.postForObject(url, request, ChatGPTResponse.class);

        System.out.println(chatGPTResponse.getChoices().get(0).getMessage().getContent());
    }


}
