package ProjektZespolowySpring.model.book;

import ProjektZespolowySpring.model.author.Author;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookDTO {

    @NotNull
    @Size (max = 255)
    private String title;

    @NotNull
    private int authorId;

    @NotNull
    private int numberOfCopies;

    public BookDTO(@NotNull @Size(max = 255) String title, @NotNull int authorId, @NotNull int numberOfCopies) {

        this.title = title;
        this.authorId = authorId;
        this.numberOfCopies = numberOfCopies;
    }

    public BookDTO() {
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }
}
