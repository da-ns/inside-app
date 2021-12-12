package lu.dans.inside24.controllers;

import lu.dans.inside24.models.Message;
import lu.dans.inside24.models.User;
import lu.dans.inside24.services.MessageService;
import lu.dans.inside24.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/message")
@RestController
public class MessageController {
    public static final String BEARER = "Bearer ";

    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @PostMapping("/send")
    public String send(@RequestParam(name = "login") String name,
                  @RequestParam(name = "message") String text,
                  @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

        String token = authorization != null && authorization.startsWith(BEARER)
                ? authorization.substring(BEARER.length())
                : null;

        User user = userService.findUserByNameAndToken(name, token);

        if (user != null) {
            if (text.trim().isEmpty()) {
                LOG.info("User {0} sent empty message.", user);
                return "Wrong data. You are sent empty message.";
            }

            messageService.createMessage(user, text);
        }

        return "Success.";
    }

    @GetMapping("/history/{login}:{count}")
    public List<Message> history(@RequestParam(name = "login") String login,
                          @RequestParam(name = "count") int count,
                          @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

        String token = authorization != null && authorization.startsWith(BEARER)
                ? authorization.substring(BEARER.length())
                : null;

        User user = userService.findUserByNameAndToken(login, token);
        return messageService.getLastUserMessages(user, count);
    }
}
