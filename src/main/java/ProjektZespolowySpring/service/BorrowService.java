package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.borrow.Borrow;
import ProjektZespolowySpring.model.borrow.BorrowDTO;

import java.util.List;
import java.util.Optional;

public interface BorrowService {

    List<BorrowDTO> findAll();

    Optional<BorrowDTO> findById(int id);

    Borrow getOne(int id);

    int add(BorrowDTO borrowDTO);

    void deleteById(int id);

    void update(int id, BorrowDTO borrowDTO);

    boolean existsById(int id);
}
