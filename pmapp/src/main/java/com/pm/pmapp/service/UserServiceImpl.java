package com.pm.pmapp.service;

import com.pm.pmapp.config.JwtProvider;
import com.pm.pmapp.model.User;
import com.pm.pmapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository repo;



    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = repo.findByEmail(email);
        if(user==null){
            throw new Exception("User not found!");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = repo.findById(userId);
        if(user.isEmpty()){
            throw new Exception("user not found!");
        }
        return user.get();
    }

    @Override
    public User updateUsersProjectSize(User user, int number) {
        user.setProjectSize(user.getProjectSize()+number);

        return repo.save(user);
    }
}
