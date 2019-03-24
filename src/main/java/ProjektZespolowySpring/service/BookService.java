package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.book.BookDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Book getOne(int id);

    void add(BookDTO bookDTO);

    void deleteById(int id);


}
