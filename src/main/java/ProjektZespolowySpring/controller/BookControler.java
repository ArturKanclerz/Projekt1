package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.exception.BadRequestException;
import ProjektZespolowySpring.exception.NotFoundException;
import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.author.AuthorRepository;
import ProjektZespolowySpring.model.book.BookDTO;
import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.book.BookRepository;
import ProjektZespolowySpring.service.AuthorService;
import ProjektZespolowySpring.service.BookService;
import ProjektZespolowySpring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class BookControler {

    private BookService bookService;
    private AuthorService authorService;

    @Autowired
    public BookControler(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;

    }

    @PostMapping("/books")
    public ResponseEntity<?> addBook(@RequestBody @Valid BookDTO bookDTO, BindingResult result) {
        checkPostErrors(bookDTO, result);
        int id = bookService.add(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/books/" + id)).build();
    }

    @GetMapping("/books")
    public List<BookDTO> getBooks(){
        return bookService.findAll();
    }

    @GetMapping("/books/{id}")
    public BookDTO getBook(@PathVariable int id){
        return bookService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updtadeAuthor(@PathVariable int id, @RequestBody @Valid BookDTO bookDTO, BindingResult result){
        checkPutErrors(bookDTO, result, id);
        bookService.update(id, bookDTO);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id){
        checkDeleteErrors(id);
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private void checkPostErrors(BookDTO bookDTO, BindingResult result){
        badRequest(result);
        if(!authorService.existsById(bookDTO.getAuthorId())){
            throw new BadRequestException("There is no author with the given id");}

    }

    private void checkPutErrors(BookDTO bookDTO, BindingResult result, int id){
        badRequest(result);
        notFound(id);
        if(!authorService.existsById(bookDTO.getAuthorId())){
            throw new BadRequestException("There is no author with the given id");
        }
    }
    private void checkDeleteErrors(int id){
        notFound(id);
    }

    private void notFound(int id) {
        if (!bookService.existsById(id)) {
            throw new NotFoundException();
        }
    }

    private void badRequest(BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(Util.getErrorMessage(result));
        }
    }

}




