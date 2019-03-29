package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.exception.BadRequestException;
import ProjektZespolowySpring.exception.NotFoundException;
import ProjektZespolowySpring.model.book.BookDTO;
import ProjektZespolowySpring.service.AuthorService;
import ProjektZespolowySpring.service.BookService;
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
public class BookController {

    private BookService bookService;
    private AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @PostMapping("/books")
    public ResponseEntity<Resource<BookDTO>> addBook(@RequestBody @Valid BookDTO bookDTO, BindingResult result) {
        checkPostErrors(bookDTO, result);
        BookDTO dto = bookService.add(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/books/" + dto.getId()))
                .body(toResource(dto));
    }

    @GetMapping("/books")
    public Resources<Resource<BookDTO>> getBooks() {
        return toResources(bookService.findAll());
    }

    @GetMapping("/books/{id}")
    public Resource<BookDTO> getBook(@PathVariable int id) {
        return toResource(bookService.findById(id).orElseThrow(NotFoundException::new));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Resource<BookDTO>> updateBook(@PathVariable int id, @RequestBody @Valid BookDTO bookDTO, BindingResult result) {
        checkPutErrors(bookDTO, result, id);
        BookDTO dto = bookService.update(id, bookDTO);
        return ResponseEntity.ok().body(toResource(dto));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        checkDeleteErrors(id);
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private void checkPostErrors(BookDTO bookDTO, BindingResult result) {
        badRequest(result);
        if (!authorService.existsById(bookDTO.getAuthorId())) {
            throw new BadRequestException("There is no author with the given id");
        }
    }

    private void checkPutErrors(BookDTO bookDTO, BindingResult result, int id) {
        badRequest(result);
        notFound(id);
        if (!authorService.existsById(bookDTO.getAuthorId())) {
            throw new BadRequestException("There is no author with the given id");
        }
    }

    private void checkDeleteErrors(int id) {
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

    private Resource<BookDTO> toResource(BookDTO dto) {
        return new Resource<>(dto,
                linkTo(BookController.class).slash("books/" + dto.getId()).withSelfRel(),
                linkTo(BookController.class).slash("books/" + dto.getId()).withRel("book"),
                linkTo(AuthorController.class).slash("authors/" + dto.getAuthorId()).withRel("author"));
    }

    private Resources<Resource<BookDTO>> toResources(List<BookDTO> list) {
        return new Resources<>(list.stream().map(this::toResource).collect(Collectors.toList()),
                linkTo(BookController.class).slash("books").withSelfRel());
    }

}




