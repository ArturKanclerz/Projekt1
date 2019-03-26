package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.model.book.BookRepository;
import ProjektZespolowySpring.model.reservation.Reservation;
import ProjektZespolowySpring.model.reservation.ReservationDTO;
import ProjektZespolowySpring.model.reservation.ReservationRepository;
import ProjektZespolowySpring.model.user.UserRepository;
import ProjektZespolowySpring.service.BookService;
import ProjektZespolowySpring.service.ReservationService;
import ProjektZespolowySpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepository userRepository;
    private UserService userService;


    @Autowired
    public ReservationController(BookService bookService, ReservationService reservationService, UserRepository userRepository) {
        this.reservationService = reservationService;
        this.bookService = bookService;
        this.userRepository = userRepository;

    }


    @PostMapping("/reservations")
    public String addReservation(@RequestBody @Valid ReservationDTO dto, BindingResult result)
    {
        if(result.hasErrors()){
            return "error";
        }
        reservationService.add(new Reservation(userRepository.getOne(dto.getUsername()),
                Calendar.getInstance(),
                bookService.getOne(dto.getBookId())));
        return "success";
    }

    @GetMapping("/reservations")
    public List<ReservationDTO> getReservations() {
        return reservationService.findAll();
    }

}
