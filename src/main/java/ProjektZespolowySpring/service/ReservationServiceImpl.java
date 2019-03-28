package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.reservation.Reservation;
import ProjektZespolowySpring.model.reservation.ReservationDTO;
import ProjektZespolowySpring.model.reservation.ReservationRepository;
import ProjektZespolowySpring.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private BookService bookService;
    private UserService userService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, BookService bookService, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    @Override
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservation -> new ReservationDTO(reservation.getId(),reservation.getReservedBook().getId(),
                        reservation.getUsername().getUsername(), reservation.getReservationDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation getOne(int id) {
        return reservationRepository.getOne(id);
    }

    @Override
    public void add(ReservationDTO dto, Authentication authentication){
        reservationRepository.save((new Reservation(new User(authentication.getName()),
                Calendar.getInstance(),
                new Book(dto.getBookId()))));
    }

    @Override
    public Optional<ReservationDTO> findById(int id) {
        return reservationRepository.findById(id).map(reservation -> new ReservationDTO(reservation.getId(),reservation.getReservedBook().getId(),reservation.getUsername().getUsername(),reservation.getReservationDate()));
    }

    @Override
    public boolean existById(int id) {
        return reservationRepository.existsById(id);
    }

    @Override
    public void update(int id, ReservationDTO dto, Authentication authentication) {
        Reservation reservation = reservationRepository.getOne(id);
        Book book = bookService.getOne(dto.getBookId());
        //User user = userService.findById();
        reservation.setId(id);
        reservation.setReservedBook(book);
        reservation.setReservationDate(Calendar.getInstance());
        reservationRepository.save(reservation);
    }

    @Override
    public void deleteById(int id) {
        reservationRepository.deleteById(id);
    }
}
