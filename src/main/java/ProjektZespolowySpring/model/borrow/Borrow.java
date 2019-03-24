package ProjektZespolowySpring.model.borrow;

import ProjektZespolowySpring.model.reservation.Reservation;

import javax.persistence.*;

@Entity
@Table(name = "borrows")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private int id;

    @OneToOne
    @JoinColumn(name = "reservationId")
    private Reservation reservation;

    public Borrow(Reservation reservation) {
        this.reservation = reservation;
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
}
