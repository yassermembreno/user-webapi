package dev.yrmg.userwebapi.userwebapi.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.yrmg.userwebapi.userwebapi.domain.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User,UUID>{
    Optional<User> findByEmail(String email);    
}
