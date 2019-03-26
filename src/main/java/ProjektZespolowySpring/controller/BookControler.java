package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.model.author.AuthorRepository;
import ProjektZespolowySpring.model.book.BookDTO;
import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.book.BookRepository;
import ProjektZespolowySpring.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class BookControler {

    private BookService bookService;

    @Autowired
    public BookControler(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public String addBook(@RequestBody @Valid BookDTO bookDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }

        bookService.add(bookDTO);
        return "success";
    }

    @GetMapping("/books")
    public List<BookDTO> getBooks(){
        return bookService.findAll();
    }

    @GetMapping("/books/{id}")
    public Optional<BookDTO> getBook(@PathVariable int id){
        return bookService.findById(id);
    }

    @PutMapping("/books/{id}")
    public String updtadeAuthor(@PathVariable int id, @RequestBody @Valid BookDTO bookDTO, BindingResult result){
        if(result.hasErrors()){
            return "error";
        }

        bookService.update(id, bookDTO);
        return "success";
    }
    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable int id){
        bookService.deleteById(id);
        return "Delete book id[" +id +"]";
    }
}



