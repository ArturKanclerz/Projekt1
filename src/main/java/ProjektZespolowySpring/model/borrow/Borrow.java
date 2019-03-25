package ProjektZespolowySpring.model.borrow;

import ProjektZespolowySpring.model.reservation.Reservation;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "borrows")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar borrowDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar returnDate;

    @OneToOne
    @JoinColumn(name = "reservationId")
    private Reservation reservation;

    public Borrow(Reservation reservation, Calendar borrowDate, Calendar returnDate) {
        this.reservation = reservation;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;

    }
    public Borrow(){

    }

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
}
