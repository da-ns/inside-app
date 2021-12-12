package lu.dans.insideapp.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "date")
    private Date date;

    public Message() {}

    public Message(User user) {
        this.user = user;
    }

    public Message(User user, String text) {
        this.user = user;
        this.text = text;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Message))
            return false;

        Message message = (Message) o;

        return Objects.equals(this.id, message.id)
                && Objects.equals(this.user, message.user)
                && Objects.equals(this.text, message.text)
                && Objects.equals(this.date, message.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.text, this.date);
    }

    @Override
    public String toString() {
        return "Message{id=" + this.id + ", user='" + this.user + "', text='" + this.text + "', date='" + this.date + "'}";
    }
}
