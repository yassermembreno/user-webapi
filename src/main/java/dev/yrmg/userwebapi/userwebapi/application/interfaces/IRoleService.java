package dev.yrmg.userwebapi.userwebapi.application.interfaces;

import java.util.List;
import java.util.Optional;

import dev.yrmg.userwebapi.userwebapi.domain.dto.RoleDto;
import dev.yrmg.userwebapi.userwebapi.domain.entities.Role;
import dev.yrmg.userwebapi.userwebapi.exception.ValidationException;

public interface IRoleService {
    Optional<Role> create(RoleDto roleDto) throws ValidationException;
    Optional<Role> findByRoleName(String roleName) throws ValidationException;
    List<Role> findAll();
}
