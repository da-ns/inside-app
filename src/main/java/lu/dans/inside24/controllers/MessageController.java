package lu.dans.inside24.controllers;

import lu.dans.inside24.controllers.errors.UnauthorizedUserError;
import lu.dans.inside24.controllers.models.Result;
import lu.dans.inside24.controllers.models.SentMessage;
import lu.dans.inside24.models.Message;
import lu.dans.inside24.models.User;
import lu.dans.inside24.services.JwtService;
import lu.dans.inside24.services.MessageDto;
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
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<Result> send(@RequestBody SentMessage sentMessage,
                                       @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization)
            throws UnauthorizedUserError {

        if (!jwtService.isValidToken(authorization, sentMessage.getLogin())) {
            LOG.error("Verification JWT token is fail.");
            throw new UnauthorizedUserError("Authorization failed.");
        }

        User user = userService.findUserByLogin(sentMessage.getLogin());

        if (user != null) {
            sentMessage.setText(sentMessage.getText().trim());

            if (sentMessage.getText().isEmpty()) {
                LOG.info("User {} sent empty message.", user);
                return new ResponseEntity<>(new Result("You are sent empty message."), HttpStatus.BAD_REQUEST);
            }

            messageService.createMessage(user, sentMessage.getText());
            LOG.info("User {} sent new message.", sentMessage.getLogin());

            return new ResponseEntity<>(new Result("Success sending."), HttpStatus.OK);
        }

        LOG.error("User {} not found.", sentMessage.getLogin());
        throw new UnauthorizedUserError("User not found.");
    }

    @GetMapping("/history/{login}/{count}")
    @ResponseBody
    public List<MessageDto> history(@PathVariable(name = "login") String login,
                                    @PathVariable(name = "count") int count,
                                    @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization)
            throws UnauthorizedUserError {

        if (!jwtService.isValidToken(authorization, login)) {
            LOG.error("Verification JWT token is fail.");
            throw new UnauthorizedUserError("Authorization failed.");
        }

        User user = userService.findUserByLogin(login);
        return messageService.getLastUserMessages(user, count);
    }
}
