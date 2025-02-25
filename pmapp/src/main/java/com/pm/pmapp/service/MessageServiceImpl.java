package com.pm.pmapp.service;

import com.pm.pmapp.model.Chat;
import com.pm.pmapp.model.Message;
import com.pm.pmapp.model.Project;
import com.pm.pmapp.model.User;
import com.pm.pmapp.repository.MessageRepository;
import com.pm.pmapp.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProjectService projectService;
    @Autowired
    private MessageRepository messageRepository;


    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepo.findById(senderId).orElseThrow(()->new Exception("User not found!"));

        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);
        Message savedMessage = messageRepository.save(message);
        chat.getMessages().add(savedMessage);
        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        List<Message> findByChatIdOrderByCreatedAtAsc = messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
        return findByChatIdOrderByCreatedAtAsc;
    }
}
