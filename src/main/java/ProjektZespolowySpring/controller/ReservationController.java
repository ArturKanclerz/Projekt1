package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.exception.BadRequestException;
import ProjektZespolowySpring.exception.NotFoundException;
import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.reservation.Reservation;
import ProjektZespolowySpring.model.reservation.ReservationDTO;
import ProjektZespolowySpring.model.user.User;
import ProjektZespolowySpring.service.BookService;
import ProjektZespolowySpring.service.ReservationService;
import ProjektZespolowySpring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {

    private ReservationService reservationService;
    private BookService bookService;


    @Autowired
    public ReservationController(ReservationService reservationService, BookService bookService) {
        this.reservationService = reservationService;
        this.bookService = bookService;
    }


    @PostMapping("/reservations")
    public ResponseEntity<?> addReservation(@RequestBody @Valid ReservationDTO dto, BindingResult result, Authentication authentication)
    {
        checkPostErrors(dto,result);
        reservationService.add(dto,authentication);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/reservations/")).build();
    }

    @GetMapping("/reservations")
    public List<ReservationDTO> getReservations(Authentication authentication) {
        return reservationService.findAll(authentication);
    }

    @GetMapping("/reservations/{id}")
    public ReservationDTO getReservation(@PathVariable int id)
    {
        return reservationService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable int id, @RequestBody @Valid ReservationDTO dto, BindingResult result, Authentication authentication)
    {
        checkPutErrors(id,result,dto);
        reservationService.update(id,dto,authentication);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable int id)
    {
        checkDeleteErrors(id);
        reservationService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private void checkDeleteErrors(int id) {
        notFound(id);
    }

    private void checkPostErrors(ReservationDTO dto, BindingResult result){
        badRequest(result);
        int countOfReservations = bookService.getCountOfBookReservations(dto.getBookId());
        int copies = bookService.getOne(dto.getBookId()).getNumberOfCopies();
        if(countOfReservations >= copies){
            throw new BadRequestException("this book is unavailable...");
        }
    }

    private void checkPutErrors(int id, BindingResult result, ReservationDTO dto)
    {
        badRequest(result);
        notFound(id);
        checkPostErrors(dto,result);
    }

    private void badRequest(BindingResult result){
        if(result.hasErrors()){
            throw new BadRequestException(Util.getErrorMessage(result));
        }
    }

    private void notFound(int id){
        if(!reservationService.existById(id)){
            throw new NotFoundException();
        }
    }
}
