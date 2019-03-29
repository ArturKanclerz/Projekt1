package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.author.AuthorDTO;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<AuthorDTO> findAll();

    Author getOne(int id);

    Optional<AuthorDTO> findById(int id);

    AuthorDTO add(AuthorDTO authorDTO);

    void deleteById(int id);

    AuthorDTO update(int id, AuthorDTO authorDTO);

    boolean existsById(int id);

    boolean existsByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

}
