package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.borrow.Borrow;
import ProjektZespolowySpring.model.borrow.BorrowDTO;

import java.util.List;

public interface BorrowService {

    List<Borrow> findAll();

    Borrow getOne(int id);

    void add(BorrowDTO borrowDTO);

    void deleteById(int id);

    void update(int id, BorrowDTO borrowDTO);
}
