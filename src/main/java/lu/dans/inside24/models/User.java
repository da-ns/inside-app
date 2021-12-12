package lu.dans.inside24.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "token")
    private String token;

    public User() {}

    public User(String login, String token) {
        this.login = login;
        this.token = token;
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

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
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
        return "Message{id=" + this.id + ", user='" + this.login + "}";
    }
}
