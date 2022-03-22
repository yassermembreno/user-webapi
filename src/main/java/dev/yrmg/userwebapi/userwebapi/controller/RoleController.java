package dev.yrmg.userwebapi.userwebapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.yrmg.userwebapi.userwebapi.application.services.RoleService;
import dev.yrmg.userwebapi.userwebapi.domain.dto.RoleDto;
import dev.yrmg.userwebapi.userwebapi.domain.entities.Role;
import dev.yrmg.userwebapi.userwebapi.shared.Response;

@RestController
@RequestMapping(value = "/api")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Response<List<RoleDto>>> getAll(){
        Response<List<RoleDto>> response = new Response<>();
        try{
            List<Role> roles = roleService.findAll();

            response.setData(roles.stream().map(r -> RoleDto.convert(r)).collect(Collectors.toList()));
            response.setSuccess(true);
            
        }catch(Exception ex){
            response.setSuccess(false);            
        }
        
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response); 
    }
}
