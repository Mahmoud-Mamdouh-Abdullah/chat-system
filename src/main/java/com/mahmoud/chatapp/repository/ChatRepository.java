package com.mahmoud.chatapp.repository;

import com.mahmoud.chatapp.entity.Application;
import com.mahmoud.chatapp.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    Optional<List<Chat>> findByApplication(Application application);
    Optional<Chat> findByChatNumberAndApplicationToken(int chatNumber, String applicationToken);
    Optional<Integer> countByApplication(Application application);
}
