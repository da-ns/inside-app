package lu.dans.inside24.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lu.dans.inside24.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Component
public class UserService {

    @Value("$(jwt.secret)")
    String jwtSecret;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public User login(String name, String password) {
        User user = findUserByNameAndPassword(name, password);

        if (user != null) {
            String token = generateToken(name);
            user.setToken(token);
            entityManager.persist(user);
            return user;
        }

        return null;
    }

    public User findUserByNameAndToken(String login, String token) {
        Query query = entityManager
                .createQuery("from User user " +
                        "where user.login = :login and user.token = :token ", User.class)
                .setParameter("login", login)
                .setParameter("token", token);

        return (User) query.getResultList().stream().filter(Objects::nonNull).findFirst().orElse(null);
    }

    private String generateToken(String name) {
        Date date = Date.from(LocalDate.now().plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject(name)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private User findUserByNameAndPassword(String login, String password) {
        Query query = entityManager
                .createQuery("from User user " +
                        "where user.login = :login and user.password = :password ", User.class)
                .setParameter("login", login)
                .setParameter("password", password);

        return (User) query.getResultList().stream().filter(Objects::nonNull).findFirst().orElse(null);
    }

    @Transactional
    public User create(User user) {
        if (user != null) {
            entityManager.persist(user);
        }

        return user;
    }
}
