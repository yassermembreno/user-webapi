package dev.yrmg.userwebapi.userwebapi.application.interfaces;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserDetailService extends UserDetailsService{
    Optional<UserDetails> getUserByJwtToken(String token);
}
