package ProjektZespolowySpring.model.loan;

import ProjektZespolowySpring.model.book.Book;
import ProjektZespolowySpring.model.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(mappedBy = "loan" )
    private List<Book> books;







}
