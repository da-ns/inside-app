package lu.dans.inside24.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lu.dans.inside24.controllers.models.Account;
import lu.dans.inside24.controllers.models.JWT;
import lu.dans.inside24.models.User;
import lu.dans.inside24.services.JwtService;
import lu.dans.inside24.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    JWT login(@RequestBody Account account) {
        String token = userService.login(account.getLogin(), account.getPassword());
        LOG.info("User {} logged in.", account.getLogin());
        return new JWT(token);
    }
}
