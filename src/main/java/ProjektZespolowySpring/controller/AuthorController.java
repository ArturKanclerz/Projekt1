package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.author.AuthorForm;
import ProjektZespolowySpring.model.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @PostMapping("/authors")
    public String addBook(@RequestBody @Valid AuthorForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }

        authorRepository.save(new Author(form.getFirstName(), form.getLastName()));
        return "success";
    }

    @GetMapping("/authors")
    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }

    @GetMapping("/authors/{id}")
    public Optional<Author> getAuthor(@PathVariable int id){
        return authorRepository.findById(id);
    }

    @DeleteMapping("/author/{id}")
    public String deleteBook(@PathVariable int id){
        authorRepository.deleteById(id);
        return "Delete autor id[" +id +"]";
    }

}
