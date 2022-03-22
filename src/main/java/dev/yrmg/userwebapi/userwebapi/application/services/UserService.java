package dev.yrmg.userwebapi.userwebapi.application.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.yrmg.userwebapi.userwebapi.application.interfaces.IUserService;
import dev.yrmg.userwebapi.userwebapi.domain.dto.UserDto;
import dev.yrmg.userwebapi.userwebapi.domain.entities.Phone;
import dev.yrmg.userwebapi.userwebapi.domain.entities.Role;
import dev.yrmg.userwebapi.userwebapi.domain.entities.User;
import dev.yrmg.userwebapi.userwebapi.exception.ValidationException;
import dev.yrmg.userwebapi.userwebapi.infrastructure.repository.IPhoneRepository;
import dev.yrmg.userwebapi.userwebapi.infrastructure.repository.IRoleRepository;
import dev.yrmg.userwebapi.userwebapi.infrastructure.repository.IUserRepository;
import dev.yrmg.userwebapi.userwebapi.security.JwtUtil;


@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private IPhoneRepository phoneRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    
    public UserService(){}
    
    @Transactional
    @Override
    public Optional<User> create(UserDto userDto) throws ValidationException {
        Optional<User> userToSave = Optional.empty();
       
        validateUser(userDto);
       
        Optional<Role> role = this.roleRepository.findByRoleName("ADMIN");
        
        if (role.isPresent()) {
            userDto.setToken(this.jwtUtil.generateToken(userDto.getEmail(), Arrays.asList(role.get())));
            userToSave = Optional.of(
                    this.userRepository.save(
                            UserDto.convert(userDto, this.passwordEncoder.encode(userDto.getPassword()),
                                    userDto.getToken(), role.get())
                    )
            );
        }
        return userToSave;        
    }

    @Override
    public Optional<User> findByEmail(String email) { 
        return userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public Optional<String> authenticate(String email, String password) throws ValidationException, AuthenticationException {
        Optional<String> token = Optional.empty();
        Optional<User> userOptional =  userRepository.findByEmail(email);

        if(!userOptional.isPresent()){
            new ValidationException(String.format("User with email {0} does not exist.",email));
        }
       
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        token = Optional.of(this.jwtUtil.generateToken(email, new ArrayList<Role>(userOptional.get().getRoles())));
        userOptional.get().setToken(token.get());
        userOptional.get().setActive(true);
        this.userRepository.save(userOptional.get());        
        
        return token;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    private void validateUser(UserDto userDto) throws ValidationException{
        Optional<User> userExists = this.userRepository.findByEmail(userDto.getEmail());

        if(userExists.isPresent()){
            throw new ValidationException("User has been registered already.");
        }

        if(!userDto.getPhones().isEmpty()){
            List<Optional<Phone>> phones = userDto.getPhones().stream()
                    .map(phone -> this.phoneRepository.findByNumber(phone.getNumber()))
                    .collect(Collectors.toList());

            for (Optional<Phone> phoneOptional : phones) {                
                if (phoneOptional.isPresent()){
                    throw new ValidationException("Phone number has been registered already.");
                }
            }       
        }
    }
    
}
