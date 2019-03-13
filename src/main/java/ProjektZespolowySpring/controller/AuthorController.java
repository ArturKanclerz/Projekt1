package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.author.AuthorForm;
import ProjektZespolowySpring.model.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AuthorController {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @PostMapping("/addAuthor")
    public String addAuthor(@RequestBody @Valid AuthorForm authorForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "error";
        }
        authorRepository.save(new Author(authorForm.getFirstName(), authorForm.getLastName()));
        return "success";

    }

    @GetMapping("/authors")
    public List<Author> all(){
        return authorRepository.findAll();
    }


}
