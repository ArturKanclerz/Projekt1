package ProjektZespolowySpring.model.user;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import ProjektZespolowySpring.model.authority.Authority;
import ProjektZespolowySpring.model.reservation.Reservation;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Authority> authorities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private List<Reservation> reservations;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        enabled = true;
        authorities = Collections.singletonList(new Authority(this, "USER"));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}

