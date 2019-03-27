package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.author.AuthorDTO;
import ProjektZespolowySpring.model.user.User;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<AuthorDTO> findAll();

    Author getOne(int id);

    Optional<AuthorDTO> findById(int id);

    int add(AuthorDTO authorDTO);

    void deleteById(int id);

    void update(int id, AuthorDTO authorDTO);

    boolean existsById(int id);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

}
