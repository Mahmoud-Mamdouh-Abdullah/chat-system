package com.mahmoud.chatapp.dto;

import com.mahmoud.chatapp.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ChatResponseDTO {
    private List<Chat> chats;
    private int total;
}
