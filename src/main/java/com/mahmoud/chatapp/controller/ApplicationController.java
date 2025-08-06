package com.mahmoud.chatapp.controller;

import com.mahmoud.chatapp.dto.ApplicationRequestDTO;
import com.mahmoud.chatapp.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ApplicationRequestDTO applicationRequestDTO) {
        return ResponseEntity.ok(applicationService.create(applicationRequestDTO));
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(applicationService.getAll());
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> getByToken(@PathVariable String token) throws Exception{
        return ResponseEntity.ok(applicationService.getByToken(token));
    }
}
