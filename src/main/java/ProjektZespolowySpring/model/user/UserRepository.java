package ProjektZespolowySpring.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsernameOrEmailAllIgnoreCase(String username, String email);

    boolean existsByUsernameNotAndEmailAllIgnoreCase(String username, String email);

}
