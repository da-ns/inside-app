package lu.dans.inside24.controllers;

import lu.dans.inside24.models.User;
import lu.dans.inside24.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    JWT login(@RequestBody Account account) {
        User user = userService.login(account.getLogin(), account.getPassword());

        if (user == null) {
            return new JWT(null);
        }

        return new JWT(user.getToken());
    }
}
