package ProjektZespolowySpring.controller;


import ProjektZespolowySpring.model.borrow.Borrow;
import ProjektZespolowySpring.model.borrow.BorrowDTO;
import ProjektZespolowySpring.model.borrow.BorrowRepository;

import ProjektZespolowySpring.model.reservation.ReservationRepository;
import ProjektZespolowySpring.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class BorrowController {

    private BorrowService borrowService;

    @Autowired
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping("/borrows")
    public String addBorrow(@RequestBody BorrowDTO borrowDTO, BindingResult result){
        if(result.hasErrors()){
            return "error";
        }

        borrowService.add(borrowDTO);
        return "success";
    }

    @GetMapping("/borrows")
    public List<BorrowDTO> getBorrows(){
        return borrowService.findAll();
    }

    @GetMapping("/borrows/{id}")
    public Optional<BorrowDTO> getBook(@PathVariable int id){
        return borrowService.findById(id);
    }

    @PutMapping("/borrows/{id}")
    public String updtadeAuthor(@PathVariable int id, @RequestBody @Valid BorrowDTO borrowDTO, BindingResult result){
        if(result.hasErrors()){
            return "error";
        }

        borrowService.update(id, borrowDTO);
        return "success";
    }

    @DeleteMapping("/borrows/{id}")
    public String deleteBorrow(@PathVariable int id){
        borrowService.deleteById(id);
        return "Delete borrow id: " +id;
    }

}
