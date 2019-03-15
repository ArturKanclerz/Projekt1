package ProjektZespolowySpring.model.book;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(nullable = false)
    private String authorFirstName;

    @Column(nullable = false)
    private  String authorLastName;

    @Column(nullable = false)
    private String title;

    public Book(){

    }

    public Book(String title, String authorFirstName, String authorLastName) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
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
}

