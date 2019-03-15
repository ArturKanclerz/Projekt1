package ProjektZespolowySpring.model.book;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookForm {

    @NotNull
    @Size (max = 255)
    private String authorFirstName;

    @NotNull
    @Size (max = 255)
    private  String authorLastName;

    @NotNull
    @Size (max = 255)
    private String title;

    public BookForm(@NotNull @Size(max = 255) String authorFirstName, @NotNull @Size(max = 255) String authorLastName, @NotNull @Size(max = 255) String title) {
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.title = title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
