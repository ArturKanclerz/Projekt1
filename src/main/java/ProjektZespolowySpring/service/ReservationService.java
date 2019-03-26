package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.reservation.Reservation;
import ProjektZespolowySpring.model.reservation.ReservationDTO;

import java.util.List;

public interface ReservationService {

    List<ReservationDTO> findAll();
    Reservation getOne(int id);
    void add(Reservation reservation);
    void deleteById(int id);
}
