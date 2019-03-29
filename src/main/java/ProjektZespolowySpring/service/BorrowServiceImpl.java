package ProjektZespolowySpring.service;

import ProjektZespolowySpring.exception.BadRequestException;
import ProjektZespolowySpring.model.borrow.Borrow;
import ProjektZespolowySpring.model.borrow.BorrowDTO;
import ProjektZespolowySpring.model.borrow.BorrowRepository;
import ProjektZespolowySpring.model.reservation.Reservation;
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
    private ReservationService reservationService;

    @Autowired
    public BorrowServiceImpl(BorrowRepository borrowRepository, ReservationRepository reservationRepository, ReservationService reservationService) {
        this.borrowRepository = borrowRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @Override
    public List<BorrowDTO> findAll() {
        return borrowRepository.findAll().stream().map(borrow -> new BorrowDTO(borrow.getId(),
                Optional.ofNullable(borrow.getReservation()).map(Reservation::getId).orElse(-1), borrow.getUsername(),
                borrow.getBorrowDate(), borrow.getReturnDate(), borrow.getDateOfReturn())).collect(Collectors.toList());
    }

    @Override
    public Optional<BorrowDTO> findById(int id) {
        return borrowRepository.findById(id).map(borrow -> new BorrowDTO(borrow.getId(),
                Optional.ofNullable(borrow.getReservation()).map(Reservation::getId).orElse(-1), borrow.getUsername(),
                borrow.getBorrowDate(), borrow.getReturnDate(), borrow.getDateOfReturn()));
    }

    @Override
    public Borrow getOne(int id) {
        return borrowRepository.getOne(id);
    }

    @Override
    public int add(BorrowDTO borrowDTO) {
        Calendar borrowDate = Calendar.getInstance();
        Calendar dateOfReturn = Calendar.getInstance();
        dateOfReturn.add(Calendar.DATE, 14);

        return borrowRepository.save(new Borrow(reservationRepository.getOne(borrowDTO.getReservationId()), borrowDate, null, dateOfReturn, reservationRepository.getOne(borrowDTO.getReservationId()).getUsername().getUsername())).getId();
    }

    @Override
    public void deleteById(int id) {
        borrowRepository.deleteById(id);
    }

    @Override
    public void update(int id, BorrowDTO borrowDTO) {
        Borrow borrow = borrowRepository.getOne(id);
        if(borrowDTO.getBorrowDate() != null){
            borrow.setBorrowDate(borrowDTO.getBorrowDate());
        }

        if(borrowDTO.getDateOfReturn() != null){
            borrow.setDateOfReturn(borrowDTO.getDateOfReturn());
        }

        borrow.setReturnDate(borrowDTO.getReturnDate());

        if(borrowDTO.getReturnDate() == null){
            if (!reservationService.existById(borrowDTO.getReservationId())) {
                throw new BadRequestException("This reservation does not exist");
            }
            borrow.setReservation(reservationRepository.getOne(borrowDTO.getReservationId()));
            borrow.setUsername(reservationRepository.getOne(borrowDTO.getReservationId()).getUsername().getUsername());
        }

        if (borrowDTO.getReturnDate() != null) {
            borrow.setReservation(null);
        }
        borrowRepository.save(borrow);
    }

    @Override
    public boolean existsById(int id) {
        return borrowRepository.existsById(id);
    }
}
