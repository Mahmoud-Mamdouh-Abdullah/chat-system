package com.mahmoud.chatapp.controller;


import com.mahmoud.chatapp.rabbitmq.ChatProducer;
import com.mahmoud.chatapp.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/applications/{token}/chats")
public class ChatController {

    private final ChatService chatService;
    private final ChatProducer chatProducer;

    public ChatController(ChatService chatService, ChatProducer chatProducer) {
        this.chatService = chatService;
        this.chatProducer = chatProducer;
    }

    @PostMapping
    public ResponseEntity<?> create(@PathVariable String token) throws Exception {
        chatProducer.sendChatCreationMessage(token);
        return ResponseEntity.accepted().body(Map.of("message", "Chat queued for processing"));
    }

    @GetMapping
    public ResponseEntity<?> get(@PathVariable String token) throws Exception {
        return ResponseEntity.ok(chatService.get(token));
    }
}
