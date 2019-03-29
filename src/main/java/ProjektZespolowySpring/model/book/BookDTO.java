package ProjektZespolowySpring.model.book;

import ProjektZespolowySpring.model.author.AuthorDTO;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Relation(collectionRelation = "books")
public class BookDTO {

    private int id;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    private int authorId;

    private AuthorDTO author;

    @NotNull
    private int numberOfCopies;

    public BookDTO(@NotNull @Size(max = 255) String title, @NotNull int authorId, @NotNull int numberOfCopies) {

        this.title = title;
        this.authorId = authorId;
        this.numberOfCopies = numberOfCopies;
    }

    public BookDTO(int id, @NotNull @Size(max = 255) String title, AuthorDTO author, @NotNull int authorId, @NotNull int numberOfCopies) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.author = author;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

}
