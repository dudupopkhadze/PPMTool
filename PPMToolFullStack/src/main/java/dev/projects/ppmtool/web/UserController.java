package dev.projects.ppmtool.web;

import dev.projects.ppmtool.domain.User;
import dev.projects.ppmtool.exceptions.UsernameAlreadyExistsResponse;
import dev.projects.ppmtool.payload.JWTLoginSuccessResponse;
import dev.projects.ppmtool.payload.LoginRequest;
import dev.projects.ppmtool.security.JWTTokenProvider;
import dev.projects.ppmtool.services.MapValidationErrorService;
import dev.projects.ppmtool.services.UserService;
import dev.projects.ppmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.validation.Valid;
import java.security.Security;

import static dev.projects.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JWTTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> autchenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        ResponseEntity<?> errors = mapValidationErrorService.MapValidationErrorService(bindingResult);
        if(errors != null) return errors;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTLoginSuccessResponse(true,jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult){
        userValidator.validate(user,bindingResult);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationErrorService(bindingResult);
        if (errorMap != null) return errorMap;

        User user1 = userService.saveUser(user);
        return new ResponseEntity<User>(user1,HttpStatus.CREATED);
    }
}
