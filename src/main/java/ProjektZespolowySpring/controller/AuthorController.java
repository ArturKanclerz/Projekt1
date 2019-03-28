package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.exception.BadRequestException;
import ProjektZespolowySpring.exception.NotFoundException;
import ProjektZespolowySpring.model.author.AuthorDTO;
import ProjektZespolowySpring.service.AuthorService;
import ProjektZespolowySpring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/authors")
    public ResponseEntity<?> addBook(@RequestBody @Valid AuthorDTO authorDTO, BindingResult result) {
        checkPostErrors(authorDTO, result);
        int id = authorService.add(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/authors/" + id)).build();
    }

    @GetMapping("/authors")
    public List<AuthorDTO> getAuthors() {
        return authorService.findAll();
    }

    @GetMapping("/authors/{id}")
    public AuthorDTO getAuthor(@PathVariable int id) {
        return authorService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<?> updtadeAuthor(@PathVariable int id, @RequestBody @Valid AuthorDTO authorDTO, BindingResult result) {
        checkPutErrors(id, result, authorDTO);
        authorService.update(id, authorDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/author/{id}")
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

}
