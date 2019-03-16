package ProjektZespolowySpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import ProjektZespolowySpring.exception.BadRequestException;
import ProjektZespolowySpring.exception.ForbiddenException;
import ProjektZespolowySpring.exception.NotFoundException;
import ProjektZespolowySpring.model.user.User;
import ProjektZespolowySpring.model.user.UserDTO;
import ProjektZespolowySpring.model.user.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @GetMapping("{username}")
    public UserDTO getUser(@PathVariable String username, Authentication authentication) {
        UserDTO dto = userRepository.findById(username)
                .map(user -> new UserDTO(user.getUsername(), user.getEmail()))
                .orElseThrow(NotFoundException::new);
        if (isAdminOrUser(authentication, username)) {
            return dto;
        }
        throw new ForbiddenException();
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody @Valid UserDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(errorMessage(result));
        } else if (userRepository.existsByUsernameOrEmailAllIgnoreCase(dto.getUsername(), dto.getEmail())) {
            throw new BadRequestException("username or email already exists");
        }
        userRepository.save(new User(dto.getUsername(), passwordEncoder.encode(dto.getPassword()), dto.getEmail()));
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/users/" + dto.getUsername())).build();
    }

    @PutMapping("{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, Authentication authentication,
                                        @RequestBody @Valid UserDTO dto, BindingResult result) {
        if (!userRepository.existsById(username)) {
            throw new NotFoundException();
        } else if (isAdminOrUser(authentication, username)) {
            if (result.hasErrors()) {
                throw new BadRequestException(errorMessage(result));
            } else if (userRepository.existsByUsernameNotAndEmailAllIgnoreCase(username, dto.getEmail())) {
                throw new BadRequestException("email already exists");
            } else {
                userRepository.save(new User(username, passwordEncoder.encode(dto.getPassword()), dto.getEmail()));
                return ResponseEntity.ok().build();
            }
        }
        throw new ForbiddenException();
    }

    @DeleteMapping("{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username, Authentication authentication) {
        if (!userRepository.existsById(username)) {
            throw new NotFoundException();
        } else if (isAdminOrUser(authentication, username)) {
            userRepository.deleteById(username);
            return ResponseEntity.ok().build();
        }
        throw new ForbiddenException();
    }

    private boolean isAdminOrUser(Authentication authentication, String username) {
        return isAdmin(authentication) || isUser(authentication, username);
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch("ADMIN"::equals);
    }

    private boolean isUser(Authentication authentication, String username) {
        return authentication.getName().equals(username);
    }

    private String errorMessage(BindingResult result) {
        return result.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
    }

}
