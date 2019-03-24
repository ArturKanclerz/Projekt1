package ProjektZespolowySpring.model.author;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthorDTO {

    @NotNull
    @Size(max=255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    public AuthorDTO(@NotNull @Size(max = 255) String firstName, @NotNull @Size(max = 255) String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AuthorDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
