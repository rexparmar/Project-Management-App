package com.pm.pmapp.repository;

import com.pm.pmapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
