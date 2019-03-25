package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.author.AuthorRepository;
import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.book.BookDTO;
import ProjektZespolowySpring.model.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private AuthorService authorService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getOne(int id) {
        return bookRepository.getOne(id);
    }

    @Override
    public void add(BookDTO bookDTO) {
        bookRepository.save(new Book(bookDTO.getTitle(), authorService.getOne(bookDTO.getAuthorId()), bookDTO.getNumberOfCopies()));

    }

    @Override
    public void deleteById(int id) {
        bookRepository.deleteById(id);

    }

    @Override
    public void update(int id, BookDTO bookDTO) {
        Book book = bookRepository.getOne(id);
        book.setId(id);
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(authorService.getOne(id));
        bookRepository.save(book);
    }
}

