package com.mahmoud.chatapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;
}
