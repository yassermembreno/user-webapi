package dev.yrmg.userwebapi.userwebapi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.yrmg.userwebapi.userwebapi.application.interfaces.IUserService;
import dev.yrmg.userwebapi.userwebapi.domain.dto.UserDto;
import dev.yrmg.userwebapi.userwebapi.domain.entities.User;
import dev.yrmg.userwebapi.userwebapi.shared.Response;


@RestController
@RequestMapping(value = "/api")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/user/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<UserDto>> register(@Valid @RequestBody UserDto userDto) {
        Response<UserDto> response = new Response<>();
        try{
            Optional<User> userOptional = this.userService.create(userDto);

            userOptional.ifPresent(user -> response.setData(UserDto.convert(user)));
            response.setMessage("Successfully.");            
            response.setSuccess(true);

        }catch(Exception ex){
            response.setSuccess(false);
            response.setMessage("Something went wrong.");
            response.setMessages(Arrays.asList(ex.getMessage()));
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/user/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<String>> login(@Valid @RequestBody UserDto request) {
        Response<String> response = new Response<>();
        try{
            Optional<String> tokenOptional = this.userService.authenticate(request.getEmail(), request.getPassword());

            if(tokenOptional.isPresent()){
                response.setData(tokenOptional.get());
                response.setSuccess(true);
                response.setMessage("Successfully.");
            }

        }catch(Exception ex){
            response.setSuccess(false);
            response.setMessage("Something went wrong.");
            response.setMessages(Arrays.asList(ex.getMessage()));
        }
        
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response);
    }
    
    @GetMapping(value = "/user/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<List<UserDto>>> getAll(){
        Response<List<UserDto>> response = new Response<>();
        try{
            List<User> users = userService.findAll();
            response.setData(users.stream().map(user -> UserDto.convert(user)).collect(Collectors.toList()));
            response.setSuccess(true);
            response.setMessage("Successfully.");
        }
        catch(Exception ex){
            response.setSuccess(false);
            response.setMessage("Something went wrong.");
            response.setMessages(Arrays.asList(ex.getMessage()));
        }

        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response);
    }
}
