package com.mahmoud.chatapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "message")
@Data
public class MessageDocument {
    @JsonIgnore
    private String id;
    private String message;
    @JsonIgnore
    private int messageNumber;
    private int chatNumber;
    private String applicationToken;
}
