package ProjektZespolowySpring.controller;


import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.book.BookForm;
import ProjektZespolowySpring.model.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    private BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @PostMapping("/addBook")
    public String addBook(@RequestBody @Valid BookForm bookForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "error";
        }
        bookRepository.save(new Book(bookForm.getTitle()));
        return "success";

    }

    @GetMapping("/books")
    public List<Book> all(){
        return bookRepository.findAll();
    }


}
