package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.exception.BadRequestException;
import ProjektZespolowySpring.exception.NotFoundException;
import ProjektZespolowySpring.model.reservation.ReservationDTO;
import ProjektZespolowySpring.service.BookService;
import ProjektZespolowySpring.service.ReservationService;
import ProjektZespolowySpring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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
    public ResponseEntity<Resource<ReservationDTO>> addReservation(@RequestBody @Valid ReservationDTO reservationDTO, BindingResult result, Authentication authentication)
    {
        checkPostErrors(reservationDTO,result, authentication);
        ReservationDTO dto = reservationService.add(reservationDTO,authentication);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/reservations/" + dto.getId()))
                .body(toResource(dto));
    }

    @GetMapping("/reservations")
    public Resources<Resource<ReservationDTO>> getReservations(Authentication authentication) {
        return toResources(reservationService.findAll(authentication));
    }

    @GetMapping("/reservations/{id}")
    public Resource<ReservationDTO> getReservation(@PathVariable int id, Authentication authentication)
    {
        checkGetErrors(id, authentication);
        return toResource(reservationService.findById(id).orElseThrow(NotFoundException::new));
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<Resource<ReservationDTO>> updateReservation(@PathVariable int id, @RequestBody @Valid ReservationDTO reservationDTO, BindingResult result, Authentication authentication)
    {
        checkPutErrors(id,result,reservationDTO,authentication);
        ReservationDTO dto = reservationService.update(id,reservationDTO,authentication);
        return ResponseEntity.ok().body(toResource(dto));
    }


    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable int id, Authentication authentication)
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
    {   if(!bookService.existsById(id)){
            throw new BadRequestException("this book does not exist");
        }
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

    private Resource<ReservationDTO> toResource(ReservationDTO dto) {
        return new Resource<>(dto,
                linkTo(ReservationController.class).slash("reservations/" + dto.getId()).withSelfRel(),
                linkTo(ReservationController.class).slash("reservations/" + dto.getId()).withRel("reservation"));
    }

    private Resources<Resource<ReservationDTO>> toResources(List<ReservationDTO> list) {
        return new Resources<>(list.stream().map(this::toResource).collect(Collectors.toList()),
                linkTo(ReservationController.class).slash("reservations").withSelfRel());
    }

}