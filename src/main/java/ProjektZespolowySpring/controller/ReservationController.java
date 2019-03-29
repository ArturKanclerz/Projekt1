package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.exception.BadRequestException;
import ProjektZespolowySpring.exception.NotFoundException;
import ProjektZespolowySpring.model.reservation.ReservationDTO;
import ProjektZespolowySpring.service.BookService;
import ProjektZespolowySpring.service.ReservationService;
import ProjektZespolowySpring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.UnableToRegisterMBeanException;
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
        checkPostErrors(dto,result, authentication);
        reservationService.add(dto,authentication);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/reservations/")).build();
    }

    @GetMapping("/reservations")
    public List<ReservationDTO> getReservations(Authentication authentication) {
        return reservationService.findAll(authentication);
    }

    @GetMapping("/reservations/{id}")
    public ReservationDTO getReservation(@PathVariable int id, Authentication authentication)
    {
        checkGetErrors(id, authentication);
        return reservationService.findById(id).orElseThrow(NotFoundException::new);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable int id, @RequestBody @Valid ReservationDTO dto, BindingResult result, Authentication authentication)
    {
        checkPutErrors(id,result,dto,authentication);
        reservationService.update(id,dto,authentication);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable int id, Authentication authentication)
    {
        checkDeleteErrors(id, authentication);
        reservationService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private void checkGetErrors(int reservationId, Authentication authentication)
    {
        notFound(reservationId);
        if( !Util.isAdmin(authentication) )
            badUser(reservationId,authentication);
    }

    private void checkPostErrors(ReservationDTO dto, BindingResult result, Authentication authentication){
        badRequest(result);
        unavailableBook(dto.getBookId());
        anotherCopyOfBook(dto.getBookId(),authentication);
    }

    private void checkPutErrors(int id, BindingResult result, ReservationDTO dto, Authentication authentication)
    {
        badRequest(result);
        notFound(id);
        if( !Util.isAdmin(authentication) )
            badUser(id, authentication);
        checkPostErrors(dto,result,authentication);
    }

    private void checkDeleteErrors(int id, Authentication authentication) {
        notFound(id);
        if( !Util.isAdmin(authentication) )
            badUser(id, authentication);
    }

    private void anotherCopyOfBook(int bookId, Authentication authentication)
    {
        if( !Util.isAdmin(authentication) ) {
            List<ReservationDTO> listOfReservations = reservationService.findAll(authentication);
            for (ReservationDTO dto : listOfReservations) {
                if (dto.getBookId() == bookId) {
                    throw new BadRequestException("You've already reserved this book.");
                }
            }
        }
    }

    private void unavailableBook(int id)
    {
        int countOfReservations = bookService.getCountOfBookReservations(id);
        int copies = bookService.getOne(id).getNumberOfCopies();
        if(countOfReservations >= copies){
            throw new BadRequestException("this book is unavailable...");
        }
    }

    private void badUser(int reservationId, Authentication authentication)
    {
        ReservationDTO dto = reservationService.findById(reservationId).orElseThrow(NotFoundException::new);

        if( !dto.getUsername().equals(authentication.getName()) ){
            throw new BadRequestException("It's not your reservation");
        }
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