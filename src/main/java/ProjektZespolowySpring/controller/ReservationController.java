package ProjektZespolowySpring.controller;

import ProjektZespolowySpring.model.book.BookRepository;
import ProjektZespolowySpring.model.reservation.Reservation;
import ProjektZespolowySpring.model.reservation.ReservationForm;
import ProjektZespolowySpring.model.reservation.ReservationRepository;
import ProjektZespolowySpring.model.user.UserRepository;
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

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private ReservationRepository reservationRepository;


    @Autowired
    public ReservationController(BookRepository bookRepository, UserRepository userRepository, ReservationRepository reservationRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }


    @PostMapping("/reservations")
    public String addReservation(@RequestBody @Valid ReservationForm form, BindingResult result)
    {
        if(result.hasErrors()){
            return "error";
        }
        reservationRepository.save(new Reservation(form.getUsername(),
                Calendar.getInstance(),
                bookRepository.getOne(form.getBookId())));
        return "success";
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        List<Reservation> check = reservationRepository.findAll();
        System.out.println(check.size());
        System.out.println(check.get(0));
        System.out.println(check.get(0));
        return reservationRepository.findAll();}
}
