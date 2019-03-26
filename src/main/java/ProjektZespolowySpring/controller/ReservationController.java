package ProjektZespolowySpring.controller;

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


    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;

    }


    @PostMapping("/reservations")
    public String addReservation(@RequestBody @Valid ReservationDTO dto, BindingResult result)
    {
        if(result.hasErrors()){
            return "error";
        }
        reservationService.add(new Reservation(new User(dto.getUsername()),
                Calendar.getInstance(),
                new Book(dto.getBookId())));
        return "success";
    }

    @GetMapping("/reservations")
    public List<ReservationDTO> getReservations() {
        return reservationService.findAll();
    }

}
