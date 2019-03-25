package ProjektZespolowySpring.service;

import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.book.BookDTO;
import ProjektZespolowySpring.model.borrow.Borrow;
import ProjektZespolowySpring.model.borrow.BorrowDTO;
import ProjektZespolowySpring.model.borrow.BorrowRepository;
import ProjektZespolowySpring.model.reservation.Reservation;
import ProjektZespolowySpring.model.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.spi.RegisterableService;
import java.util.Calendar;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService{

    private BorrowRepository borrowRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public BorrowServiceImpl(BorrowRepository borrowRepository, ReservationRepository reservationRepository){
        this.borrowRepository = borrowRepository;
        this.reservationRepository = reservationRepository;
    }
    @Override
    public List<Borrow> findAll() {
        return borrowRepository.findAll();
    }

    @Override
    public Borrow getOne(int id) {
        return borrowRepository.getOne(id);
    }

    @Override
    public void add(BorrowDTO borrowDTO) {
        Calendar borrowDate = Calendar.getInstance();
        Calendar returnDate = Calendar.getInstance();
        returnDate.add(Calendar.DATE, 14);

        borrowRepository.save(new Borrow(reservationRepository.getOne(borrowDTO.getReservationId()), borrowDate, returnDate));
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
}
