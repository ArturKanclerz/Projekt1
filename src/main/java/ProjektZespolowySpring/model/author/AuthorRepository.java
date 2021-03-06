package ProjektZespolowySpring.model.author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    boolean existsByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);
}
