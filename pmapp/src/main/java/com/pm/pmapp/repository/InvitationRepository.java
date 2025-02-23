package com.pm.pmapp.repository;

import com.pm.pmapp.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation,Long> {
    Invitation findByToken(String token);
    Invitation findByEmail(String userEmail);
}
