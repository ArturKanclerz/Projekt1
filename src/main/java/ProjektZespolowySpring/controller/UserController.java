package ProjektZespolowySpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import ProjektZespolowySpring.model.user.UserDTO;
import ProjektZespolowySpring.service.UserService;
import ProjektZespolowySpring.util.Util;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Resources<Resource<UserDTO>> getAllUsers() {
        return toResources(userService.findAll());
    }

    @GetMapping("{username}")
    public Resource<UserDTO> getUser(@PathVariable String username, Authentication authentication) {
        checkGetErrors(username, authentication);
        return toResource(userService.findById(username).orElseThrow(NotFoundException::new));
    }

    @PostMapping
    public ResponseEntity<Resource<UserDTO>> addUser(@RequestBody @Valid UserDTO userDTO, BindingResult result) {
        checkPostErrors(userDTO, result);
        UserDTO dto = userService.add(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/users/" + dto.getUsername()))
                .body(toResource(dto));
    }

    @PutMapping("{username}")
    public ResponseEntity<Resource<UserDTO>> updateUser(@PathVariable String username, Authentication authentication,
                                                        @RequestBody @Valid UserDTO userDTO, BindingResult result) {
        checkPutErrors(username, authentication, userDTO, result);
        UserDTO dto = userService.update(username, userDTO);
        return ResponseEntity.ok().body(toResource(dto));
    }

    @DeleteMapping("{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username, Authentication authentication) {
        checkDeleteErrors(username, authentication);
        userService.deleteById(username);
        return ResponseEntity.ok().build();
    }

    private void checkGetErrors(String username, Authentication authentication) {
        forbidden(username, authentication);
    }

    private void checkPostErrors(UserDTO dto, BindingResult result) {
        badRequest(result);
        if (userService.existsByUsernameOrEmailAllIgnoreCase(dto.getUsername(), dto.getEmail())) {
            throw new BadRequestException("username or email already exists");
        }
    }

    private void checkPutErrors(String username, Authentication authentication, UserDTO dto, BindingResult result) {
        forbidden(username, authentication);
        notFound(username);
        badRequest(result);
        if (userService.existsByUsernameNotAndEmailAllIgnoreCase(username, dto.getEmail())) {
            throw new BadRequestException("email already exists");
        }
    }

    private void checkDeleteErrors(String username, Authentication authentication) {
        forbidden(username, authentication);
        notFound(username);
    }

    private void forbidden(String username, Authentication authentication) {
        if (!Util.isAdminOrUser(authentication, username)) {
            throw new ForbiddenException();
        }
    }

    private void notFound(String username) {
        if (!userService.existsById(username)) {
            throw new NotFoundException();
        }
    }

    private void badRequest(BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(Util.getErrorMessage(result));
        }
    }

    private Resource<UserDTO> toResource(UserDTO dto) {
        return new Resource<>(dto,
                linkTo(UserController.class).slash(dto.getUsername()).withSelfRel(),
                linkTo(UserController.class).slash(dto.getUsername()).withRel("user"));
    }

    private Resources<Resource<UserDTO>> toResources(List<UserDTO> list) {
        return new Resources<>(list.stream().map(this::toResource).collect(Collectors.toList()),
                linkTo(UserController.class).withSelfRel());
    }

}
