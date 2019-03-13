package ProjektZespolowySpring.model.book;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.loan.Loan;
import ProjektZespolowySpring.model.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(nullable = false)
    private String title;

    @Column
    private boolean isLoan;

    @ManyToMany
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "bookId", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "authorId", referencedColumnName = "ID")
    )
    private List<Author> authors;

    @ManyToOne
    @JoinColumn(name = "books")
    private Loan loan;

    public Book(String title){
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLoan() {
        return isLoan;
    }

    public void setLoan(boolean loan) {
        isLoan = loan;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}
