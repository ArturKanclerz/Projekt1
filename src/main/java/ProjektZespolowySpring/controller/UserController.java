package ProjektZespolowySpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import javax.validation.Valid;

import ProjektZespolowySpring.model.user.User;
import ProjektZespolowySpring.model.user.UserForm;
import ProjektZespolowySpring.model.user.UserRepository;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/library/user")
    public String main(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid UserForm form, BindingResult result) {
        if (result.hasErrors() || userRepository.existsByUsernameOrEmail(form.getUsername(), form.getEmail())) {
            return "error";
        }
        userRepository.save(new User(form.getUsername(), passwordEncoder.encode(form.getPassword()), form.getEmail()));
        return "success";
    }

}
