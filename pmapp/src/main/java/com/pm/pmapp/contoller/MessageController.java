package com.pm.pmapp.contoller;

import com.pm.pmapp.model.Chat;
import com.pm.pmapp.model.Message;
import com.pm.pmapp.model.User;
import com.pm.pmapp.request.CreateMessageRequest;
import com.pm.pmapp.service.MessageService;
import com.pm.pmapp.service.ProjectService;
import com.pm.pmapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MessageService messageService;

    private CreateMessageRequest request;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest req ) throws Exception{
        User user  = userService.findUserById(request.getSenderId());
        if(user==null){
            throw new Exception("User not found!");
        }
        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();
        if(chats==null){
            throw new Exception("No chats found with this project!");
        }
        Message sentMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(),
                request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId) throws Exception{
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }



}
