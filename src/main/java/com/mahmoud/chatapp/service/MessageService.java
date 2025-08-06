package com.mahmoud.chatapp.service;

import com.mahmoud.chatapp.dto.MessageDocumentResponseDTO;
import com.mahmoud.chatapp.dto.MessageRequestDTO;
import com.mahmoud.chatapp.dto.MessageResponseDTO;
import com.mahmoud.chatapp.entity.Chat;
import com.mahmoud.chatapp.entity.Message;
import com.mahmoud.chatapp.entity.MessageDocument;
import com.mahmoud.chatapp.repository.ChatRepository;
import com.mahmoud.chatapp.repository.MessageRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private static final Logger log = LoggerFactory.getLogger(MessageService.class);
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticSearchService elasticSearchService;

    public MessageService(MessageRepository messageRepository,
                          ChatRepository chatRepository,
                          ElasticSearchService elasticSearchService, ElasticsearchOperations elasticsearchOperations) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticSearchService = elasticSearchService;
    }

    public Message create(String token, int chatNumber, MessageRequestDTO messageRequestDTO) throws Exception {
        Chat chat = chatRepository.findByChatNumberAndApplicationToken(chatNumber, token)
                .orElseThrow(() -> new BadRequestException("Chat not found"));

        Message messageEntity = new Message();
        messageEntity.setMessage(messageRequestDTO.getMessage());
        messageEntity.setMessageNumber(chat.getMessageCount() + 1);
        messageEntity.setChat(chat);
        chat.setMessageCount(chat.getMessageCount() + 1);

        elasticSearchService.indexMessage(messageEntity, token, chatNumber);

        return messageRepository.save(messageEntity);
    }

    public MessageResponseDTO get(String token, int chatNumber) throws Exception {
        Chat chat = chatRepository.findByChatNumberAndApplicationToken(chatNumber, token)
                .orElseThrow(() -> new BadRequestException("Chat not found"));

        List<Message> messages = messageRepository.findByChat(chat)
                .orElse(null);
        return new MessageResponseDTO(messages, messages != null ? messages.size() : 0);
    }

    public MessageDocumentResponseDTO elasticSearch(String token, int chatNumber, String keyword) throws Exception {
        String sanitizedKeyword = keyword.trim();
        
        if (sanitizedKeyword.isEmpty()) {
            throw new BadRequestException("Search keyword cannot be empty");
        }
        Criteria criteria = new Criteria("applicationToken").is(token)
                .and("chatNumber").is(chatNumber)
                .and("message").matches(sanitizedKeyword);

        Query searchQuery = new CriteriaQuery(criteria);
        log.info("Searching for messages in Elasticsearch with query: {}", searchQuery);

        SearchHits<MessageDocument> searchHits = elasticsearchOperations.search(
                searchQuery,
                MessageDocument.class
        );

        List<MessageDocument> docs = searchHits.stream()
                .map(SearchHit::getContent)
                .toList();

        return new MessageDocumentResponseDTO(docs, docs.size());
    }
}