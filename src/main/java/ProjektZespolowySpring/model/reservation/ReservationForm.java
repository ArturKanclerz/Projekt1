package ProjektZespolowySpring.model.reservation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReservationForm {

    @NotNull
    private int bookId;

    private String username;

    public ReservationForm( @NotNull int bookId, @NotNull @Size(max = 255) String username) {
        this.bookId = bookId;
        this.username = username;
    }

    public ReservationForm() {
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
