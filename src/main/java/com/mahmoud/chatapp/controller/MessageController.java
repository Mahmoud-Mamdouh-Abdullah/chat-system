package com.mahmoud.chatapp.controller;

import com.mahmoud.chatapp.dto.MessageQueueDTO;
import com.mahmoud.chatapp.dto.MessageRequestDTO;
import com.mahmoud.chatapp.service.MessageProducer;
import com.mahmoud.chatapp.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/applications/{token}/chats/{chatNumber}/messages")
public class MessageController {

    private final MessageService messageService;
    private final MessageProducer messageProducer;

    public MessageController(MessageService messageService, MessageProducer messageProducer) {
        this.messageService = messageService;
        this.messageProducer = messageProducer;
    }

    @PostMapping
    public ResponseEntity<?> create(@PathVariable String token, @PathVariable int chatNumber, @RequestBody @Valid MessageRequestDTO messageRequestDTO) throws Exception {
        MessageQueueDTO messageQueueDTO = new MessageQueueDTO();
        messageQueueDTO.setToken(token);
        messageQueueDTO.setChatNumber(chatNumber);
        messageQueueDTO.setMessage(messageRequestDTO.getMessage());

        messageProducer.sendMessage(messageQueueDTO);

        return ResponseEntity.accepted().body(Map.of("message", "Message queued for processing"));
    }

    @GetMapping
    public ResponseEntity<?> get(@PathVariable String token, @PathVariable int chatNumber) throws Exception {
        return ResponseEntity.ok(messageService.get(token, chatNumber));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@PathVariable String token, @PathVariable int chatNumber, @RequestParam String keyword) throws Exception {
        return ResponseEntity.ok(messageService.elasticSearch(token, chatNumber, keyword));
    }

}
