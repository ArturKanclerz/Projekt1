package ProjektZespolowySpring.model.borrow;

import ProjektZespolowySpring.model.reservation.Reservation;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "borrows")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar borrowDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar returnDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateOfReturn;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "reservationId")
    private Reservation reservation;

    public Borrow(Reservation reservation, Calendar borrowDate, Calendar returnDate, Calendar dateOfReturn) {
        this.reservation = reservation;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dateOfReturn = dateOfReturn;

    }

    public Borrow() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Calendar getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Calendar borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Calendar getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Calendar returnDate) {
        this.returnDate = returnDate;
    }

    public Calendar getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Calendar dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }
}
