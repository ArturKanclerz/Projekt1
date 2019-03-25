package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.author.AuthorDTO;
import ProjektZespolowySpring.model.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;


    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;

    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getOne(int id) {
        return authorRepository.getOne(id);
    }

    @Override
    public void add(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void deleteById(int id) {
        authorRepository.deleteById(id);

    }

    @Override
    public void update(int id, AuthorDTO authorDTO) {
        Author author = authorRepository.getOne(id);
        author.setId(id);
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        authorRepository.save(author);
    }
}
