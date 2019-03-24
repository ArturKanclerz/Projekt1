package ProjektZespolowySpring.controller;


import ProjektZespolowySpring.model.borrow.Borrow;
import ProjektZespolowySpring.model.borrow.BorrowDTO;
import ProjektZespolowySpring.model.borrow.BorrowRepository;

import ProjektZespolowySpring.model.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BorrowController {

    private ReservationRepository reservationRepository;
    private BorrowRepository borrowRepository;

    @Autowired
    public BorrowController(ReservationRepository reservationRepository, BorrowRepository borrowRepository) {
        this.reservationRepository = reservationRepository;
        this.borrowRepository = borrowRepository;
    }

    @PostMapping("/borrows")
    public String addBorrow(@RequestBody BorrowDTO borrowDTO, BindingResult result){
        if(result.hasErrors()){
            return "error";
        }

        borrowRepository.save(new Borrow(reservationRepository.getOne(borrowDTO.getReservationId())));
        return "success";
    }
    @GetMapping("/borrows")
    public List<Borrow> getBorrows(){
        return borrowRepository.findAll();
    }

    @GetMapping("/borrows/{id}")
    public Optional<Borrow> getBook(@PathVariable int id){
        return borrowRepository.findById(id);
    }

    @DeleteMapping("/borrows/{id}")
    public String deleteBorrow(@PathVariable int id){
        borrowRepository.deleteById(id);
        return "Delete borrow id: " +id;
    }

}
