package lu.dans.insideapp.services;

import lu.dans.insideapp.models.Message;
import lu.dans.insideapp.models.User;
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

    public List<MessageDto> getLastUserMessages(User user, int count) {
        Query query = entityManager
                .createQuery("from Message message " +
                        "where message.user.id = :userId " +
                        "order by message.date desc", Message.class)
                .setParameter("userId", user.getId())
                .setMaxResults(count);

        List<Message> messages = query.getResultList();

        return messages
                .stream()
                .map(message -> new MessageDto(message.getId(), message.getText(), message.getDate()))
                .toList();
    }

    @Transactional
    public void createMessage(User user, String text) {
        Message message = new Message();
        message.setUser(user);
        message.setText(text);

        entityManager.persist(message);
    }
}
