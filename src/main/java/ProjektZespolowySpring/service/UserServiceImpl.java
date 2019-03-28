package ProjektZespolowySpring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ProjektZespolowySpring.model.user.User;
import ProjektZespolowySpring.model.user.UserDTO;
import ProjektZespolowySpring.model.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> findAll() {
        System.out.println(passwordEncoder.encode("12345"));
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findById(String id) {
        return userRepository.findById(id).map(user -> new UserDTO(user.getUsername(), user.getEmail()));
    }

    @Override
    public void add(UserDTO dto) {
        userRepository.save(new User(dto.getUsername(), passwordEncoder.encode(dto.getPassword()), dto.getEmail()));
    }

    @Override
    public void update(String id, UserDTO dto) {
        userRepository.save(new User(id, passwordEncoder.encode(dto.getPassword()), dto.getEmail()));
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByUsernameOrEmailAllIgnoreCase(String username, String email) {
        return userRepository.existsByUsernameOrEmailAllIgnoreCase(username, email);
    }

    @Override
    public boolean existsByUsernameNotAndEmailAllIgnoreCase(String username, String email) {
        return userRepository.existsByUsernameNotAndEmailAllIgnoreCase(username, email);
    }

}
