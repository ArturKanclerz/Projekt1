package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.author.AuthorDTO;
import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.book.BookDTO;
import ProjektZespolowySpring.model.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return bookRepository.findAll().stream().map(book -> new BookDTO(book.getId(), book.getTitle(), new AuthorDTO(book.getAuthor().getFirstName(), book.getAuthor().getLastName(), book.getAuthor().getId()), book.getAuthor().getId(), book.getNumberOfCopies())).collect(Collectors.toList());
    }

    @Override
    public Optional<BookDTO> findById(int id) {
        return bookRepository.findById(id).map(book -> new BookDTO(book.getId(), book.getTitle(), new AuthorDTO(book.getAuthor().getFirstName(), book.getAuthor().getLastName(), book.getAuthor().getId()), book.getAuthor().getId(), book.getNumberOfCopies()));
    }

    @Override
    public Book getOne(int id) {
        return bookRepository.getOne(id);
    }

    @Override
    public BookDTO add(BookDTO bookDTO) {
        Book b = bookRepository.save(new Book(bookDTO.getTitle(), authorService.getOne(bookDTO.getAuthorId()), bookDTO.getNumberOfCopies()));
        return new BookDTO(b.getId(), b.getTitle(), new AuthorDTO(b.getAuthor().getFirstName(), b.getAuthor().getLastName(), b.getAuthor().getId()), b.getAuthor().getId(), b.getNumberOfCopies());
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
    public BookDTO update(int id, BookDTO bookDTO) {
        Book book = bookRepository.getOne(id);
        book.setId(id);
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(authorService.getOne(bookDTO.getAuthorId()));
        book.setNumberOfCopies(bookDTO.getNumberOfCopies());
        Book b = bookRepository.save(book);
        return new BookDTO(b.getId(), b.getTitle(), new AuthorDTO(b.getAuthor().getFirstName(), b.getAuthor().getLastName(), b.getAuthor().getId()), b.getAuthor().getId(), b.getNumberOfCopies());
    }

    @Override
    public boolean existsById(int id) {
        return bookRepository.existsById(id);
    }
}

