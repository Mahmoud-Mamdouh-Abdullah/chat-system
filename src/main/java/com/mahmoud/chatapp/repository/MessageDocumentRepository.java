package com.mahmoud.chatapp.repository;

import com.mahmoud.chatapp.entity.MessageDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MessageDocumentRepository extends ElasticsearchRepository<MessageDocument, String> {
}
