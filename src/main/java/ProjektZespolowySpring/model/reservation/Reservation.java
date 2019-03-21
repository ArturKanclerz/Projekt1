package ProjektZespolowySpring.model.reservation;


import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.user.User;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

//    @ManyToOne
//    @JoinColumn(name = "reservingUsername")
//    private User reserving;

    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar reservationDate;

    @OneToOne
    @JoinColumn(name = "bookID")
    private Book reservedBook;

    public Reservation(String reserving, Calendar reservationDate, Book reservedBook) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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
