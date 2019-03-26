package ProjektZespolowySpring.service;

import java.util.List;
import java.util.Optional;

import ProjektZespolowySpring.model.user.User;
import ProjektZespolowySpring.model.user.UserDTO;

public interface UserService {

    List<UserDTO> findAll();

    Optional<UserDTO> findById(String id);

    void add(UserDTO dto);

    void update(String id, UserDTO dto);

    void deleteById(String id);

    boolean existsById(String id);

    boolean existsByUsernameOrEmailAllIgnoreCase(String username, String email);

    boolean existsByUsernameNotAndEmailAllIgnoreCase(String username, String email);

}
