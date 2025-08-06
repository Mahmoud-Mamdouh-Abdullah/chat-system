package com.mahmoud.chatapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MessageRequestDTO {
    @NotBlank(message = "Message is required")
    private String message;
}
