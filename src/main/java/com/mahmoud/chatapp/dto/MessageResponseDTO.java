package com.mahmoud.chatapp.dto;

import com.mahmoud.chatapp.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageResponseDTO {
    private List<Message> messages;
    private int total;
}
