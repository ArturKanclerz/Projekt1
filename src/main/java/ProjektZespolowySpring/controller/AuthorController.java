package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.author.AuthorDTO;
import ProjektZespolowySpring.model.author.AuthorRepository;
import ProjektZespolowySpring.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/authors")
    public String addBook(@RequestBody @Valid AuthorDTO form, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }

        authorService.add(new Author(form.getFirstName(), form.getLastName()));
        return "success";
    }

    @GetMapping("/authors")
    public List<Author> getAuthors(){
        return authorService.findAll();
    }

    @GetMapping("/authors/{id}")
    public Author getAuthor(@PathVariable int id){
        return authorService.getOne(id);
    }

    @DeleteMapping("/author/{id}")
    public String deleteBook(@PathVariable int id){
        authorService.deleteById(id);
        return "Delete autor id[" +id +"]";
    }

}
