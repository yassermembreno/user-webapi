package dev.yrmg.userwebapi.userwebapi.domain.dto;

import dev.yrmg.userwebapi.userwebapi.domain.entities.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserDto {
    private UUID id;
    private String name;
    
    @NotNull(message = "Email cannot be null.")
    @NotBlank(message = "Email cannot be empty or blank")
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", 
          message = "Invalid email format.")
    private String email;
    
    @NotNull(message = "Password cannot be null.")
    @NotBlank(message = "Password cannot be empty or blank.")
    private String password;

    private String token;
    private boolean isActive;
    private Date lastLogin;
    private Date createdOn;
    private Date modifiedOn;
    private List<PhoneDto> phones;
    private List<RoleDto> roles;
    
    public UserDto(){

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public List<PhoneDto> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDto> phones) {
        this.phones = phones;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    
    public static User convert(UserDto userDto, String passwordEncoded, String token, Role role){
        final User user = new User();

        if(userDto == null || passwordEncoded == null || token == null || role == null) {
            throw new IllegalArgumentException("Something went wrong, objects cannot be null.");
        }
        Date now = new Date();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoded);
        user.setToken(token);
        user.setActive(true);
        user.setLastLogin(now);
        user.setRoles(new HashSet<>(Arrays.asList(role))); 
        user.setCreatedOn(now);
        user.setModifiedOn(now);

        Set<Phone> phones = userDto.getPhones().stream().map(phoneDto -> {
            Phone phone = new Phone();
            phone.setNumber(phoneDto.getNumber());
            phone.setCityCode(phoneDto.getCityCode());
            phone.setCountryCode(phoneDto.getCountryCode());
            phone.setCreatedOn(now);
            phone.setModifiedOn(now);
            phone.setUser(user);
            return phone;
        }).collect(Collectors.toSet());

        user.setPhones(new HashSet<>(phones));

        return user;
    }

    public static UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        //userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setToken(user.getToken());
        userDto.setActive(user.isActive());
        userDto.setCreatedOn(user.getCreatedOn());
        userDto.setModifiedOn(user.getModifiedOn());
        userDto.setLastLogin(user.getLastLogin());
        System.out.println(user.getPassword());
        // userDto.setPhones(user.getPhones().stream().map(
        //     phone -> new PhoneDto(
        //             phone.getNumber(),
        //             phone.getCityCode(),
        //             phone.getCountryCode()
        //     )).collect(Collectors.toList()));

        // userDto.setRoles(user.getRoles().stream().map(
        //         role -> new RoleDto(
        //                 role.getRoleName(),
        //                 role.getDescription()
        //         )).collect(Collectors.toList()));
        

        return userDto;
    }

}
