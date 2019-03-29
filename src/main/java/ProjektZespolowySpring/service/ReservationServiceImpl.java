package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.reservation.Reservation;
import ProjektZespolowySpring.model.reservation.ReservationDTO;
import ProjektZespolowySpring.model.reservation.ReservationRepository;
import ProjektZespolowySpring.model.user.User;
import ProjektZespolowySpring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private BookService bookService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, BookService bookService) {
        this.reservationRepository = reservationRepository;
        this.bookService = bookService;
    }

    @Override
    public List<ReservationDTO> findAll(Authentication authentication) {

        List<ReservationDTO> list = reservationRepository.findAll().stream()
                .map(reservation -> new ReservationDTO(reservation.getId(), reservation.getReservedBook().getId(),
                        reservation.getUsername().getUsername(), reservation.getReservationDate()))
                .collect(Collectors.toList());
        if (Util.isAdmin(authentication)) {
            return list;
        }
        List<ReservationDTO> output = new ArrayList<>();
        for (ReservationDTO dto : list) {
            if (dto.getUsername().equals(authentication.getName())) {
                output.add(dto);
            }
        }
        return output;
    }

    @Override
    public Reservation getOne(int id) {
        return reservationRepository.getOne(id);
    }

    @Override
    public ReservationDTO add(ReservationDTO dto, Authentication authentication) {
        Reservation r = reservationRepository.save((new Reservation(new User(authentication.getName()),
                Calendar.getInstance(),
                new Book(dto.getBookId()))));
        return new ReservationDTO(r.getId(), r.getReservedBook().getId(), r.getUsername().getUsername(), r.getReservationDate());
    }

    @Override
    public Optional<ReservationDTO> findById(int id) {
        return reservationRepository.findById(id).map(reservation -> new ReservationDTO(reservation.getId(), reservation.getReservedBook().getId(), reservation.getUsername().getUsername(), reservation.getReservationDate()));
    }

    @Override
    public boolean existById(int id) {
        return reservationRepository.existsById(id);
    }

    @Override
    public ReservationDTO update(int id, ReservationDTO dto, Authentication authentication) {
        Reservation reservation = reservationRepository.getOne(id);
        Book book = bookService.getOne(dto.getBookId());
        reservation.setId(id);
        reservation.setReservedBook(book);
        reservation.setReservationDate(Calendar.getInstance());
        Reservation r = reservationRepository.save(reservation);
        return new ReservationDTO(r.getId(), r.getReservedBook().getId(), r.getUsername().getUsername(), r.getReservationDate());
    }

    @Override
    public void deleteById(int id) {
        reservationRepository.deleteById(id);
    }
}
