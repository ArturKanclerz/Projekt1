package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.author.AuthorDTO;
import ProjektZespolowySpring.model.user.User;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> findAll();

    Author getOne(int id);

    void add(Author author);

    void deleteById(int id);

    void update(int id, AuthorDTO authorDTO);
}
