package dev.yrmg.userwebapi.userwebapi.domain.dto;

import dev.yrmg.userwebapi.userwebapi.domain.entities.Role;

public class RoleDto {
    private String roleName;
    private String description;

    public RoleDto(String roleName, String description){
        this.roleName = roleName;
        this.description = description;
    }   

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Role convert(RoleDto roleDto){        
        Role role = new Role();
        
        role.setRoleName(roleDto.getRoleName());
        role.setDescription(roleDto.getDescription());

        return role;
    }

    public static RoleDto convert(Role role){
        return new RoleDto(role.getRoleName(), role.getDescription());
    }
    
}
