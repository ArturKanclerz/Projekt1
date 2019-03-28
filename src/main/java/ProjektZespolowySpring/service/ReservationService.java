package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.reservation.Reservation;
import ProjektZespolowySpring.model.reservation.ReservationDTO;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    List<ReservationDTO> findAll();
    Reservation getOne(int id);
    void add(ReservationDTO dto, Authentication authentication);

    Optional<ReservationDTO> findById(int id);
    boolean existById(int id);

    void update(int id, ReservationDTO dto, Authentication authentication);

    void deleteById(int id);
}
