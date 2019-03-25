package ProjektZespolowySpring.model.author;

import ProjektZespolowySpring.model.authority.Authority;
import ProjektZespolowySpring.model.book.Book;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<Book> books;

    public Author(){

    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }
    public Author(int id, String firstName, String lastName) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
