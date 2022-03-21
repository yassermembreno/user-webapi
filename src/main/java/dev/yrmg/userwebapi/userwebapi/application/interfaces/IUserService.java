package dev.yrmg.userwebapi.userwebapi.application.interfaces;

import java.util.List;
import java.util.Optional;

import dev.yrmg.userwebapi.userwebapi.domain.dto.UserDto;
import dev.yrmg.userwebapi.userwebapi.domain.entities.User;

public interface IUserService {
    Optional<User> create(UserDto userDto);
    Optional<User> findByEmail(String email);
    Optional<String> authenticate(String email, String password);
    List<User> findAll();
}
