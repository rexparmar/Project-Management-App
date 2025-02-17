package com.pm.pmapp.service;

import com.pm.pmapp.model.Chat;
import com.pm.pmapp.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRepository repo;
    @Override
    public Chat createChat(Chat chat) {
        return repo.save(chat);
    }
}
