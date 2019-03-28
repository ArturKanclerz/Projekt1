package ProjektZespolowySpring.model.reservation;


import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.user.User;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;


    @ManyToOne
    @JoinColumn(name = "username")
    private User username;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar reservationDate;


    @ManyToOne
    @JoinColumn(name = "reservedBook")
    private Book reservedBook;

    public Reservation(User reserving, Calendar reservationDate, Book reservedBook) {
        this.username = reserving;
        this.reservationDate = reservationDate;
        this.reservedBook = reservedBook;
    }

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public Calendar getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Calendar reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Book getReservedBook() {
        return reservedBook;
    }

    public void setReservedBook(Book reservedBook) {
        this.reservedBook = reservedBook;
    }
}
