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
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream().map(book -> new BookDTO(book.getId(), book.getTitle(),book.getAuthor().getId(),book.getNumberOfCopies(), book.getNumberOfBorrowedCopies())).collect(Collectors.toList());
    }

    @Override
    public Optional<BookDTO> findById(int id){
        return bookRepository.findById(id).map(book -> new BookDTO(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getNumberOfCopies(), book.getNumberOfBorrowedCopies()));
    }

    @Override
    public Book getOne(int id) {
        return bookRepository.getOne(id);

    }

    @Override
    public int add(BookDTO bookDTO) {
        return bookRepository.save(new Book(bookDTO.getTitle(), authorService.getOne(bookDTO.getAuthorId()), bookDTO.getNumberOfCopies())).getId();

    }

    @Override
    public int getCountOfBookReservations(int bookID) {

        return bookRepository.getOne(bookID).getListOfReservations().size();
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

    @Override
    public boolean existsById(int id) {
        return bookRepository.existsById(id);
    }
}

