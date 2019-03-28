package ProjektZespolowySpring.model.borrow;

import javax.validation.constraints.NotNull;
import java.util.Calendar;

public class BorrowDTO {

    private int id;

    @NotNull
    private int reservationId;

    private Calendar borrowDate;

    private Calendar returnDate;

    private Calendar dateOfReturn;

    public BorrowDTO(@NotNull int reservationId) {
        this.reservationId = reservationId;
    }

    public BorrowDTO(int id, @NotNull int reservationId, Calendar borrowDate, Calendar returnDate, Calendar dateOfReturn) {
        this.id = id;
        this.reservationId = reservationId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dateOfReturn = dateOfReturn;
    }

    public BorrowDTO(){}

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Calendar dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }
}
