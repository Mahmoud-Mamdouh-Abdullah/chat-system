package com.mahmoud.chatapp.service;

import com.mahmoud.chatapp.entity.Message;
import com.mahmoud.chatapp.entity.MessageDocument;
import com.mahmoud.chatapp.repository.MessageDocumentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ElasticSearchService {

    private final MessageDocumentRepository messageDocumentRepository;

    public ElasticSearchService(MessageDocumentRepository messageDocumentRepository) {
        this.messageDocumentRepository = messageDocumentRepository;
    }

    public void indexMessage(Message msg, String token, int chatNumber) {
        MessageDocument doc = new MessageDocument();
        doc.setId(UUID.randomUUID().toString());
        doc.setMessage(msg.getMessage());
        doc.setMessageNumber(msg.getMessageNumber());
        doc.setChatNumber(chatNumber);
        doc.setApplicationToken(token);
        messageDocumentRepository.save(doc);
    }
}
