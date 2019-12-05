package dev.projects.ppmtool.services;

import dev.projects.ppmtool.domain.User;
import dev.projects.ppmtool.exceptions.UsernameAlreadyExistsException;
import dev.projects.ppmtool.repositories.UserRepository;
import dev.projects.ppmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User user){
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            user.setUsername(user.getUsername());
            user.setConfirmPassword("");
            return userRepository.save(user);
        } catch (Exception e) {
            throw new  UsernameAlreadyExistsException("User with Username: '"+user.getUsername()+"' already Exists" );
        }
    }
}
