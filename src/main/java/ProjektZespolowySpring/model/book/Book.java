package ProjektZespolowySpring.model.book;

import ProjektZespolowySpring.model.author.Author;
import ProjektZespolowySpring.model.reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

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


    private int numberOfBorrowedCopies;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservedBook")
    private List<Reservation> listOfReservations;


    public Book(){

    }

    public Book(String title, Author author, int numberOfCopies) {
        this.title = title;
        this.author = author;
        this.numberOfCopies = numberOfCopies;
        this.numberOfBorrowedCopies = 0;
    }

    public Book(int id){
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

    public int getNumberOfBorrowedCopies() {
        return numberOfBorrowedCopies;
    }

    public void setNumberOfBorrowedCopies(int numberOfBorrowedCopies) {
        this.numberOfBorrowedCopies = numberOfBorrowedCopies;
    }
}