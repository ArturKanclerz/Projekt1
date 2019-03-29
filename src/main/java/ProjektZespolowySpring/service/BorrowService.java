package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.borrow.Borrow;
import ProjektZespolowySpring.model.borrow.BorrowDTO;

import java.util.List;
import java.util.Optional;

public interface BorrowService {

    List<BorrowDTO> findAll();

    Optional<BorrowDTO> findById(int id);

    Borrow getOne(int id);

    BorrowDTO add(BorrowDTO borrowDTO);

    void deleteById(int id);

    BorrowDTO update(int id, BorrowDTO borrowDTO);

    boolean existsById(int id);
}
