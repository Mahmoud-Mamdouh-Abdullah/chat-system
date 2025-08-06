package com.mahmoud.chatapp.service;

import com.mahmoud.chatapp.dto.ApplicationRequestDTO;
import com.mahmoud.chatapp.dto.ApplicationResponseDTO;
import com.mahmoud.chatapp.entity.Application;
import com.mahmoud.chatapp.repository.ApplicationRepository;
import com.mahmoud.chatapp.repository.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final ChatRepository chatRepository;

    public ApplicationService (ApplicationRepository applicationRepository, ChatRepository chatRepository) {
        this.applicationRepository = applicationRepository;
        this.chatRepository = chatRepository;
    }

    public Application create(ApplicationRequestDTO applicationRequestDTO) {
        Application application = new Application();
        application.setName(applicationRequestDTO.getName());
        application.setToken(UUID.randomUUID().toString());

        return applicationRepository.save(application);
    }

    public ApplicationResponseDTO getAll() {
        List<Application> applications = applicationRepository.findAll();
        return new ApplicationResponseDTO(applications, applications.size());
    }

    public Application getByToken(String token) throws Exception {
        Application application = applicationRepository.findByToken(token).orElse(null);
        if(application == null) {
            throw new BadRequestException("Application not found");
        }
        return application;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void updateCount() {
        List<Application> applications = applicationRepository.findAll();
        applications.forEach(application -> {
            int chatsCount = chatRepository.countByApplication(application).orElse(0);
            log.info("Updating application {} chat count to {}", application.getName(), chatsCount);
            application.setChatCount(chatsCount);
        });
        applicationRepository.saveAll(applications);
    }
}
