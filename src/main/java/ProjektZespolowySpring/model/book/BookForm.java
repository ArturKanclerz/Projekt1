package ProjektZespolowySpring.model.book;

import ProjektZespolowySpring.model.author.Author;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookForm {

    @NotNull
    @Size (max = 255)
    private String title;

    @NotNull
    private int authorId;

    public BookForm(@NotNull @Size(max = 255) String title, @NotNull int authorId) {

        this.title = title;
        this.authorId = authorId;

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
}
