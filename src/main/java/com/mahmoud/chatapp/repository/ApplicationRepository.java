package com.mahmoud.chatapp.repository;

import com.mahmoud.chatapp.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    Optional<Application> findByToken(String token);
}
