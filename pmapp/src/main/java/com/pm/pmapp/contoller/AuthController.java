package com.pm.pmapp.contoller;

import com.pm.pmapp.config.JwtProvider;
import com.pm.pmapp.model.Subscription;
import com.pm.pmapp.model.User;
import com.pm.pmapp.repository.UserRepository;
import com.pm.pmapp.request.LoginRequest;
import com.pm.pmapp.response.AuthResponse;
import com.pm.pmapp.service.CustomUserDetailsImpl;
import com.pm.pmapp.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CustomUserDetailsImpl userDetails;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsImpl customUserDetailsImpl;

    @Autowired
    private SubscriptionService subscriptionService;
//  This method is helpful for creating user. Parameter taking is User. Return type is ResponseEntity. we have a post mapping
//    with signup url.
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws Exception{
//        First we check if we already have user registered on our platform or not.
        User isUserExist = userRepository.findByEmail(user.getEmail());
//        If we do then we throw an exception saying user already exists.
        if(isUserExist!=null){
            throw new Exception("Account already exists with : "+ user.getEmail());
        }
//        If not then we create a new user. and set the attributes.
        User createdUser = new User();
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());

//      We save the user in the database.
        User savedUser = userRepository.save(createdUser);
        subscriptionService.createSubscription(savedUser);
//        We create a new authentication, using email and password. Then we set the authentication using getting
//        context.
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        Then we create a jwt token for that user.
        String jwt = JwtProvider.generateToken(authentication);

//        We create new auth response. And then send a mesasge that user has been successfully created. and then we set the
//        jwt token as the response token.
        AuthResponse response = new AuthResponse();
        response.setMessage("Sign up success!!");
        response.setJwt(jwt);

//        We return the response with jwt authentication token and http status as created.
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



//      We redirect user to the signin api when url ends with "/signin".
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setMessage("Sign in success!!");
        response.setJwt(jwt);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsImpl.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("User not found!!!");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Incorrect password!");
        }
        return new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities());
    }
}
