package com.pm.pmapp.service;

import com.pm.pmapp.model.Invitation;
import com.pm.pmapp.repository.InvitationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService{
    @Autowired
    private InvitationRepository repo;
    @Autowired
    private EmailService emailService;


    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {
        String invitationToken = UUID.randomUUID().toString();
        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);

        repo.save(invitation);

        String invitationLink="http://localhost:5173/accept_invitation?"+invitationToken;
        emailService.sendEmailWithToken(email,invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {
        Invitation invitation = repo.findByToken(token);
        if(invitation==null){
            throw new Exception("Invitation token not found!");
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userMail) {
        Invitation invitation = repo.findByEmail(userMail);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation = repo.findByToken(token);
        repo.delete(invitation);
    }
}
