package com.example.angula.controller;

import com.example.angula.tools.BasicTools;
import com.example.angula.tools.UserTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/ollama")
public class OllamaController {

    private final ChatClient chatClient;

    public OllamaController(ChatClient.Builder builder,
                            UserTools userTools,
                            BasicTools basicTools) {
        this.chatClient = builder
                .defaultTools(userTools, basicTools)
                .build();
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String prompt) {
        log.info("prompt: {}", prompt);
        String response = chatClient.prompt()
                .system("""
                        You are a smart assistant. Use available tools and your intelligence to answer user's queries.
                        """)
                .user(prompt)
                .call()
                .content();
        return ResponseEntity.ok(response);
    }
}
