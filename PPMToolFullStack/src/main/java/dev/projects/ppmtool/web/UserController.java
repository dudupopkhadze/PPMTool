package dev.projects.ppmtool.web;

import dev.projects.ppmtool.domain.User;
import dev.projects.ppmtool.exceptions.UsernameAlreadyExistsResponse;
import dev.projects.ppmtool.services.MapValidationErrorService;
import dev.projects.ppmtool.services.UserService;
import dev.projects.ppmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult){
        userValidator.validate(user,bindingResult);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationErrorService(bindingResult);
        if (errorMap != null) return errorMap;

        User user1 = userService.saveUser(user);
        return new ResponseEntity<User>(user1,HttpStatus.CREATED);
    }
}
