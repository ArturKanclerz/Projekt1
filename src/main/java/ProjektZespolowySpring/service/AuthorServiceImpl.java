package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.author.AuthorDTO;
import ProjektZespolowySpring.model.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(author -> new AuthorDTO(author.getFirstName(), author.getLastName(), author.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDTO> findById(int id) {
        return authorRepository.findById(id).map(author -> new AuthorDTO(author.getFirstName(), author.getLastName(), author.getId()));
    }

    @Override
    public Author getOne(int id) {
        return authorRepository.getOne(id);
    }

    @Override
    public AuthorDTO add(AuthorDTO authorDTO) {
        Author a = authorRepository.save(new Author(authorDTO.getFirstName(), authorDTO.getLastName()));
        return new AuthorDTO(a.getFirstName(), a.getLastName(), a.getId());
    }

    @Override
    public void deleteById(int id) {
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDTO update(int id, AuthorDTO authorDTO) {
        Author author = authorRepository.getOne(id);
        author.setId(id);
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        Author a = authorRepository.save(author);
        return new AuthorDTO(a.getFirstName(), a.getLastName(), a.getId());
    }

    @Override
    public boolean existsById(int id) {
        return authorRepository.existsById(id);
    }

    @Override
    public boolean existsByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName) {
        return authorRepository.existsByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
    }
}
