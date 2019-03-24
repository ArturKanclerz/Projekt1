package ProjektZespolowySpring.model.borrow;

import javax.validation.constraints.NotNull;

public class BorrowDTO {
    @NotNull
    private int reservationId;

    public BorrowDTO(@NotNull int reservationId) {
        this.reservationId = reservationId;
    }

    public BorrowDTO(){

    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
}
