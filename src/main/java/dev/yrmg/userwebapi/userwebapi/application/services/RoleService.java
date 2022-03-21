package dev.yrmg.userwebapi.userwebapi.application.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.yrmg.userwebapi.userwebapi.application.interfaces.IRoleService;
import dev.yrmg.userwebapi.userwebapi.domain.dto.RoleDto;
import dev.yrmg.userwebapi.userwebapi.domain.entities.Role;
import dev.yrmg.userwebapi.userwebapi.exception.ValidationException;
import dev.yrmg.userwebapi.userwebapi.infrastructure.repository.IRoleRepository;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    public RoleService(){}    
    
    @Transactional
    @Override
    public Optional<Role> create(RoleDto roleDto) throws ValidationException{
        Optional<Role> rolePersisted = findByRoleName(roleDto.getRoleName());

        rolePersisted.ifPresent(r -> {try {
            throw new ValidationException(String.format("Role with Name: {0} already exist.", r.getRoleName()));
        } catch (ValidationException e) {            
            e.printStackTrace();
        }} );
        
        return Optional.of(roleRepository.save(RoleDto.convert(roleDto)));
    }

    @Override
    public Optional<Role> findByRoleName(String roleName) throws ValidationException{
        return Optional.ofNullable(roleRepository.findByRoleName(roleName).orElseThrow(() 
                        -> new ValidationException(String.format("Role with Name: {0} was not found.", roleName))));
    }

    @Override
    public List<Role> findAll() {        
        return roleRepository.findAll();
    }
    
}
