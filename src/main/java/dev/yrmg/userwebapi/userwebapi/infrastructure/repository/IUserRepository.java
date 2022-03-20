package dev.yrmg.userwebapi.userwebapi.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.yrmg.userwebapi.userwebapi.domain.entities.User;

public interface IUserRepository extends JpaRepository<User,UUID>{
    Optional<User> findByEmail(String email);    
}
