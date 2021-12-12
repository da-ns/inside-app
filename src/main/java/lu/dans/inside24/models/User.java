package lu.dans.inside24.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    public User() {}

    public User(String login) {
        this.login = login;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof User))
            return false;

        User user = (User) o;

        return Objects.equals(this.id, user.id) && Objects.equals(this.login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.login);
    }

    @Override
    public String toString() {
        return "User{id=" + this.id + ", user='" + this.login + "}";
    }
}
