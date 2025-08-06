package com.mahmoud.chatapp.dto;

import com.mahmoud.chatapp.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApplicationResponseDTO {
    private List<Application> applications;
    private int total;
}
