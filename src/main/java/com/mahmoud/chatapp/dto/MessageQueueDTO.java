package com.mahmoud.chatapp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageQueueDTO implements Serializable {
    private String token;
    private int chatNumber;
    private String message;

    @Override
    public String toString() {
        return "MessageQueueDTO{" +
                "token='" + token + '\'' +
                ", chatNumber=" + chatNumber +
                ", message='" + message + '\'' +
                '}';
    }
}
