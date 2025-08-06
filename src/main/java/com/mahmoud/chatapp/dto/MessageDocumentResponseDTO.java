package com.mahmoud.chatapp.dto;

import com.mahmoud.chatapp.entity.MessageDocument;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageDocumentResponseDTO {
    private List<MessageDocument> messages;
    private int total;
}
