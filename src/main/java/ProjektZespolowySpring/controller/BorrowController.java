package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.exception.BadRequestException;
import ProjektZespolowySpring.exception.NotFoundException;
import ProjektZespolowySpring.model.borrow.BorrowDTO;

import ProjektZespolowySpring.service.BorrowService;
import ProjektZespolowySpring.service.ReservationService;
import ProjektZespolowySpring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class BorrowController {

    private BorrowService borrowService;
    private ReservationService reservationService;

    @Autowired
    public BorrowController(BorrowService borrowService, ReservationService reservationService) {
        this.borrowService = borrowService;
        this.reservationService = reservationService;
    }

    @PostMapping("/borrows")
    public ResponseEntity<?> addBorrow(@RequestBody BorrowDTO borrowDTO, BindingResult result) {
        checkPostErrors(borrowDTO, result);
        int id = borrowService.add(borrowDTO);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/borrows/" + id)).build();
    }

    @GetMapping("/borrows")
    public List<BorrowDTO> getBorrows() {
        return borrowService.findAll();
    }

    @GetMapping("/borrows/{id}")
    public BorrowDTO getBorrow(@PathVariable int id) {
        return borrowService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PutMapping("/borrows/{id}")
    public ResponseEntity<?> updtadeBorrow(@PathVariable int id, @RequestBody @Valid BorrowDTO borrowDTO, BindingResult result) {
        checkPutErrors(borrowDTO, result, id);
        borrowService.update(id, borrowDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/borrows/{id}")
    public ResponseEntity<?> deleteBorrow(@PathVariable int id) {
        checkDeleteErrors(id);
        borrowService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private void checkPostErrors(BorrowDTO borrowDTO, BindingResult result) {
        badRequest(result);
        sameReservation(borrowDTO.getReservationId());
        if (!reservationService.existById(borrowDTO.getReservationId())) {
            throw new BadRequestException("This reservation does not exist");
        }
    }

    private void checkPutErrors(BorrowDTO borrowDTO, BindingResult result, int id) {
        badRequest(result);
        notFound(id);
    }

    private void checkDeleteErrors(int id) {
        notFound(id);
    }

    private void notFound(int id) {
        if (!borrowService.existsById(id)) {
            throw new NotFoundException();
        }
    }

    private void badRequest(BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(Util.getErrorMessage(result));
        }
    }

    private void sameReservation(int reservationId){
        List<BorrowDTO> listOfBorrows = borrowService.findAll();
        for (BorrowDTO dto : listOfBorrows) {
            if (dto.getReservationId() == reservationId) {
                throw new BadRequestException("This reservation is completed");
            }
        }
    }
}
