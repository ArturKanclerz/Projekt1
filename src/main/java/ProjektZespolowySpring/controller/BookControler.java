package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.model.book.BookForm;
import ProjektZespolowySpring.model.book.Book;
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
public class BookControler {

    private BookRepository bookRepository;

    @Autowired
    public BookControler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/books")
    public String addBook(@RequestBody @Valid BookForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }

        bookRepository.save(new Book(form.getTitle(), form.getAuthorFirstName(), form.getAuthorLastName()));
        return "success";
    }

    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }
}



