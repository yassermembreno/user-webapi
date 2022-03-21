package dev.yrmg.userwebapi.userwebapi.application.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.AuthenticationException;

import dev.yrmg.userwebapi.userwebapi.domain.dto.UserDto;
import dev.yrmg.userwebapi.userwebapi.domain.entities.User;
import dev.yrmg.userwebapi.userwebapi.exception.ValidationException;

public interface IUserService {
    Optional<User> create(UserDto userDto) throws ValidationException;
    Optional<User> findByEmail(String email);
    Optional<String> authenticate(String email, String password)throws ValidationException, AuthenticationException;
    List<User> findAll();
}
