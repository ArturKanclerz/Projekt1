package ProjektZespolowySpring.model.reservation;


import javax.validation.constraints.NotNull;
import java.util.Calendar;

public class ReservationDTO {

    private int id;
    @NotNull
    private int bookId;

    private String username;

    private Calendar reservationDate;

    public ReservationDTO(@NotNull int bookId, @NotNull String username) {
        this.bookId = bookId;
        this.username = username;
    }

    public ReservationDTO(int id, @NotNull int bookId, String username, Calendar reservationDate) {
        this.id = id;
        this.bookId = bookId;
        this.username = username;
        this.reservationDate = reservationDate;
    }

    public ReservationDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Calendar reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
