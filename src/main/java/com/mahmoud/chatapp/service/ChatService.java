package com.mahmoud.chatapp.service;

import com.mahmoud.chatapp.dto.ChatResponseDTO;
import com.mahmoud.chatapp.entity.Application;
import com.mahmoud.chatapp.entity.Chat;
import com.mahmoud.chatapp.repository.ApplicationRepository;
import com.mahmoud.chatapp.repository.ChatRepository;
import com.mahmoud.chatapp.repository.MessageRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    private final ApplicationRepository applicationRepository;

    private final MessageRepository messageRepository;

    public ChatService(ChatRepository chatRepository
            , ApplicationRepository applicationRepository
            , MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.applicationRepository = applicationRepository;
        this.messageRepository = messageRepository;
    }

    public ChatResponseDTO get(String token) throws Exception {
        Application application = applicationRepository.findByToken(token).orElse(null);
        if(application == null) {
            throw new BadRequestException("Application not found");
        }
        List<Chat> chats = chatRepository.findByApplication(application).orElse(null);
        return new ChatResponseDTO(chats, chats != null ? chats.size() : 0);
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void updateMessagesCount() {
        List<Chat> chats = chatRepository.findAll();
        chats.forEach(chat -> {
            int messageCount = messageRepository.countByChat(chat).orElse(0);
            chat.setMessageCount(messageCount);
        });
        chatRepository.saveAll(chats);
    }
}
