package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.model.authority.Authority;
import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.book.BookRepository;
import ProjektZespolowySpring.model.reservation.Reservation;
import ProjektZespolowySpring.model.reservation.ReservationDTO;
import ProjektZespolowySpring.model.reservation.ReservationRepository;
import ProjektZespolowySpring.model.user.User;
import ProjektZespolowySpring.model.user.UserRepository;
import ProjektZespolowySpring.service.BookService;
import ProjektZespolowySpring.service.ReservationService;
import ProjektZespolowySpring.service.UserService;
import ProjektZespolowySpring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Calendar;
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
    public String addReservation(@RequestBody @Valid ReservationDTO dto, BindingResult result, Authentication authentication)
    {
        if(result.hasErrors()){
            return "error";
        }
        int countOfReservations = bookService.getCountOfBookReservations(dto.getBookId());
        int copies = bookService.getOne(dto.getBookId()).getNumberOfCopies();
        if(countOfReservations < copies){
            reservationService.add(new Reservation(new User(authentication.getName()),
                    Calendar.getInstance(),
                    new Book(dto.getBookId())));
            return "success";
        } else {
            return "this book is unavailable...";
        }

    }

    @GetMapping("/reservations")
    public List<ReservationDTO> getReservations() {


        return reservationService.findAll();
    }


}
