package com.mahmoud.chatapp.service;

import com.mahmoud.chatapp.dto.MessageQueueDTO;
import com.mahmoud.chatapp.entity.Chat;
import com.mahmoud.chatapp.entity.Message;
import com.mahmoud.chatapp.rabbitmq.RabbitMQConfig;
import com.mahmoud.chatapp.repository.ChatRepository;
import com.mahmoud.chatapp.repository.MessageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ElasticSearchService elasticSearchService;

    public MessageConsumer(ChatRepository chatRepository, MessageRepository messageRepository, ElasticSearchService elasticSearchService) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.elasticSearchService = elasticSearchService;
    }

    @RabbitListener(queues = RabbitMQConfig.MESSAGE_QUEUE)
    public void handleMessage(MessageQueueDTO dto) {
        try {
            Chat chat = chatRepository.findByChatNumberAndApplicationToken(dto.getChatNumber(), dto.getToken())
                    .orElseThrow(() -> new RuntimeException("Chat not found"));

            Message messageEntity = new Message();
            messageEntity.setMessage(dto.getMessage());
            messageEntity.setMessageNumber(chat.getMessageCount() + 1);
            messageEntity.setChat(chat);

            chat.setMessageCount(chat.getMessageCount() + 1);

            messageRepository.save(messageEntity);

            elasticSearchService.indexMessage(messageEntity, dto.getToken(), dto.getChatNumber());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
