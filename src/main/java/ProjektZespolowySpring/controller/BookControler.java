package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.model.author.AuthorRepository;
import ProjektZespolowySpring.model.book.BookForm;
import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class BookControler {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public BookControler(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @PostMapping("/books")
    public String addBook(@RequestBody @Valid BookForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }

        bookRepository.save(new Book(form.getTitle(), authorRepository.getOne(form.getAuthorId()) ));
        return "success";
    }

    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public Optional<Book> getBook(@PathVariable int id){
        return bookRepository.findById(id);
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable int id){
        bookRepository.deleteById(id);
        return "Delete book id[" +id +"]";
    }


}



