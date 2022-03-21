package dev.yrmg.userwebapi.userwebapi.application.services;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.Optional;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.yrmg.userwebapi.userwebapi.application.interfaces.IUserDetailService;
import dev.yrmg.userwebapi.userwebapi.domain.entities.User;
import dev.yrmg.userwebapi.userwebapi.infrastructure.repository.IUserRepository;
import dev.yrmg.userwebapi.userwebapi.security.JwtUtil;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class UserDetailService implements IUserDetailService{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public UserDetailService(){

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;

        user = userRepository.findByEmail(email).get();
        if(user == null){
            throw new UsernameNotFoundException(String.format("User with email: {0} not found.", email));
        }

        return withUsername(user.getEmail())
        .password(user.getPassword())
        .authorities(user.getRoles())
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
    }

    @Override
    public Optional<UserDetails> getUserByJwtToken(String token) {
       if(jwtUtil.validateToken(token)){
           return Optional.of(withUsername(jwtUtil.getUser(token))
           .authorities(jwtUtil.getRoles(token))
           .password("")
           .accountExpired(false)
           .accountLocked(false)
           .credentialsExpired(false)
           .disabled(false)
           .build());
       }

       return Optional.empty();
    }
    
}
