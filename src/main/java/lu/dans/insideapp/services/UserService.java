package lu.dans.insideapp.services;

import lu.dans.insideapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Objects;

@Component
public class UserService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    JwtService jwtService;

    public String login(String login, String password) {
        User user = findUserByLoginAndPassword(login, password);

        if (user != null) {
            return jwtService.generateToken(login);
        }

        return null;
    }

    public User findUserByLogin(String login) {
        Query query = entityManager
                .createQuery("from User user " +
                        "where user.login = :login", User.class)
                .setParameter("login", login);

        return (User) query.getResultList().stream().filter(Objects::nonNull).findFirst().orElse(null);
    }

    private User findUserByLoginAndPassword(String login, String password) {
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
