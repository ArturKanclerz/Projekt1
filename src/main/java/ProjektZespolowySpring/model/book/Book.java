package ProjektZespolowySpring.model.book;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.borrow.Borrow;
import ProjektZespolowySpring.model.reservation.Reservation;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author")
    private Author author;

    @Column(nullable = false)
    private int numberOfCopies;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservedBook")
    private List<Reservation> listOfReservations;

    public Book() {
    }

    public Book(String title, Author author, int numberOfCopies) {
        this.title = title;
        this.author = author;
        this.numberOfCopies = numberOfCopies;
    }

    public Book(int id) {
        this.id = id;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public List<Reservation> getListOfReservations() {
        return listOfReservations;
    }

    public void setListOfReservations(List<Reservation> listOfReservations) {
        this.listOfReservations = listOfReservations;
    }

}