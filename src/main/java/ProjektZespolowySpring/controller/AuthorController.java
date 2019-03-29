package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.exception.BadRequestException;
import ProjektZespolowySpring.exception.NotFoundException;
import ProjektZespolowySpring.model.author.AuthorDTO;
import ProjektZespolowySpring.service.AuthorService;
import ProjektZespolowySpring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/authors")
    public ResponseEntity<Resource<AuthorDTO>> addBook(@RequestBody @Valid AuthorDTO authorDTO, BindingResult result) {
        checkPostErrors(authorDTO, result);
        AuthorDTO dto = authorService.add(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/authors/" + dto.getId()))
                .body(toResource(dto));
    }

    @GetMapping("/authors")
    public Resources<Resource<AuthorDTO>> getAuthors() {
        return toResources(authorService.findAll());
    }

    @GetMapping("/authors/{id}")
    public Resource<AuthorDTO> getAuthor(@PathVariable int id) {
        return toResource(authorService.findById(id).orElseThrow(NotFoundException::new));
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<Resource<AuthorDTO>> updtadeAuthor(@PathVariable int id, @RequestBody @Valid AuthorDTO authorDTO, BindingResult result) {
        checkPutErrors(id, result, authorDTO);
        AuthorDTO dto = authorService.update(id, authorDTO);
        return ResponseEntity.ok().body(toResource(dto));
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable int id) {
        checkDeleteErrors(id);
        authorService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private void checkPostErrors(AuthorDTO authorDTO, BindingResult result) {
        badRequest(result);
        if (authorService.existsByFirstNameAndLastNameAllIgnoreCase(authorDTO.getFirstName(), authorDTO.getLastName())) {
            throw new BadRequestException("Author is already exists");
        }
    }

    private void checkPutErrors(int id, BindingResult result, AuthorDTO authorDTO) {
        badRequest(result);
        notFound(id);
        if (authorService.existsByFirstNameAndLastNameAllIgnoreCase(authorDTO.getFirstName(), authorDTO.getLastName())) {
            throw new BadRequestException("Author is already exists");
        }
    }

    private void checkDeleteErrors(int id) {
        notFound(id);
    }

    private void notFound(int id) {
        if (!authorService.existsById(id)) {
            throw new NotFoundException();
        }
    }

    private void badRequest(BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(Util.getErrorMessage(result));
        }
    }

    private Resource<AuthorDTO> toResource(AuthorDTO dto) {
        return new Resource<>(dto,
                linkTo(AuthorController.class).slash("authors/" + dto.getId()).withSelfRel(),
                linkTo(AuthorController.class).slash("authors/" + dto.getId()).withRel("author"));
    }

    private Resources<Resource<AuthorDTO>> toResources(List<AuthorDTO> list) {
        return new Resources<>(list.stream().map(this::toResource).collect(Collectors.toList()),
                linkTo(AuthorController.class).slash("authors").withSelfRel());
    }

}
