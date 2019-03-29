package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.book.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDTO> findAll();

    Optional<BookDTO> findById(int id);

    Book getOne(int id);

    BookDTO add(BookDTO bookDTO);

    int getCountOfBookReservations(int bookID);

    void deleteById(int id);

    BookDTO update(int id, BookDTO bookDTO);

    boolean existsById(int id);
}
