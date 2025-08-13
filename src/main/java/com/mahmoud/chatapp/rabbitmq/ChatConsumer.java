package com.mahmoud.chatapp.rabbitmq;

import com.mahmoud.chatapp.entity.Application;
import com.mahmoud.chatapp.entity.Chat;
import com.mahmoud.chatapp.repository.ApplicationRepository;
import com.mahmoud.chatapp.repository.ChatRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ChatConsumer {

    private final ChatRepository chatRepository;
    private final ApplicationRepository applicationRepository;

    public ChatConsumer(ChatRepository chatRepository, ApplicationRepository applicationRepository) {
        this.chatRepository = chatRepository;
        this.applicationRepository = applicationRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.CHAT_QUEUE)
    public void handleChatCreation(String token) {
        try {
            Application application = applicationRepository.findByToken(token).orElse(null);
            if(application == null) {
                throw new BadRequestException("Application not found");
            }
            Chat chat = new Chat();
            chat.setApplication(application);
            chat.setChatNumber(application.getChatCount() + 1);
            chatRepository.save(chat);

            application.setChatCount(application.getChatCount() + 1);
            applicationRepository.save(application);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}