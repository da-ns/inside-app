package lu.dans.inside24.services;

import lu.dans.inside24.models.Message;
import lu.dans.inside24.models.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class MessageService {

    @PersistenceContext
    EntityManager entityManager;

    public List<Message> getLastUserMessages(User user, int count) {
        Query query = entityManager
                .createQuery("from Message message " +
                        "where message.user.id = :userId " +
                        "order by message.date desc", Message.class)
                .setParameter("userId", user)
                .setMaxResults(count);

        return (List<Message>) query.getResultList();
    }

    @Transactional
    public void createMessage(User user, String text) {
        Message message = new Message();
        message.setUser(user);
        message.setText(text);

        entityManager.persist(message);
    }
}
