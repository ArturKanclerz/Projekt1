package ProjektZespolowySpring.model.book;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookForm {

    @NotNull
    @Size(max = 255)
    private String title;

    public BookForm(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
