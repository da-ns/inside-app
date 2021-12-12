package lu.dans.insideapp.controllers;

import lu.dans.insideapp.controllers.models.Account;
import lu.dans.insideapp.controllers.models.JWT;
import lu.dans.insideapp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
