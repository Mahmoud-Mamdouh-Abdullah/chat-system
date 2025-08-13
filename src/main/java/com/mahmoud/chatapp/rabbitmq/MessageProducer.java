package com.mahmoud.chatapp.rabbitmq;

import com.mahmoud.chatapp.dto.MessageQueueDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(MessageQueueDTO messageQueueDTO) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.MESSAGE_ROUTING_KEY,
                messageQueueDTO
        );
    }
}
