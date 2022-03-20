package dev.yrmg.userwebapi.userwebapi.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.yrmg.userwebapi.userwebapi.domain.entities.Role;

public interface IRoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByRoleName(String name);
}
