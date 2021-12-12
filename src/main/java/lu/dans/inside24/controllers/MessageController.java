package lu.dans.inside24.controllers;

import lu.dans.inside24.models.Message;
import lu.dans.inside24.models.User;
import lu.dans.inside24.services.MessageService;
import lu.dans.inside24.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("message")
@RestController
public class MessageController {
    public static final String BEARER = "Bearer ";

    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<Result> send(@RequestBody SentMessage sentMessage,
                                       @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

        String token = authorization != null && authorization.startsWith(BEARER)
                ? authorization.substring(BEARER.length())
                : null;

        User user = userService.findUserByLoginAndToken(sentMessage.getLogin(), token);

        if (user != null) {
            sentMessage.setText(sentMessage.getText().trim());

            if (sentMessage.getText().isEmpty()) {
                LOG.info("User {0} sent empty message.", user);
                return new ResponseEntity<>(new Result("Wrong data. You are sent empty message."),
                        HttpStatus.BAD_REQUEST);
            }

            messageService.createMessage(user, sentMessage.getText());

            return new ResponseEntity<>(new Result("Success sending."), HttpStatus.OK);
        }

        return new ResponseEntity<>(new Result("You are not logged in. <a href='/'>Go to login</a>."), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/history/{login}:{count}")
    public List<Message> history(@RequestParam(name = "login") String login,
                          @RequestParam(name = "count") int count,
                          @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {

        String token = authorization != null && authorization.startsWith(BEARER)
                ? authorization.substring(BEARER.length())
                : null;

        User user = userService.findUserByLoginAndToken(login, token);
        return messageService.getLastUserMessages(user, count);
    }
}
