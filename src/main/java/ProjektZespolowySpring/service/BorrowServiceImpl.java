package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.borrow.Borrow;
import ProjektZespolowySpring.model.borrow.BorrowDTO;
import ProjektZespolowySpring.model.borrow.BorrowRepository;
import ProjektZespolowySpring.model.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowServiceImpl implements BorrowService {

    private BorrowRepository borrowRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public BorrowServiceImpl(BorrowRepository borrowRepository, ReservationRepository reservationRepository) {
        this.borrowRepository = borrowRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<BorrowDTO> findAll() {
        return borrowRepository.findAll().stream().map(borrow -> new BorrowDTO(borrow.getId(), borrow.getReservation().getId(), borrow.getBorrowDate(), borrow.getReturnDate())).collect(Collectors.toList());
    }

    @Override
    public Optional<BorrowDTO> findById(int id) {
        return borrowRepository.findById(id).map(borrow -> new BorrowDTO(borrow.getId(), borrow.getReservation().getId(), borrow.getBorrowDate(), borrow.getReturnDate()));
    }

    @Override
    public Borrow getOne(int id) {
        return borrowRepository.getOne(id);
    }

    @Override
    public int add(BorrowDTO borrowDTO) {
        Calendar borrowDate = Calendar.getInstance();
        Calendar returnDate = Calendar.getInstance();
        returnDate.add(Calendar.DATE, 14);

        return borrowRepository.save(new Borrow(reservationRepository.getOne(borrowDTO.getReservationId()), borrowDate, returnDate)).getId();
    }

    @Override
    public void deleteById(int id) {
        borrowRepository.deleteById(id);
    }

    @Override
    public void update(int id, BorrowDTO borrowDTO) {
        Borrow borrow = borrowRepository.getOne(id);
        borrow.setReservation(reservationRepository.getOne(borrowDTO.getReservationId()));
    }

    @Override
    public boolean existsById(int id) {
        return borrowRepository.existsById(id);
    }
}
