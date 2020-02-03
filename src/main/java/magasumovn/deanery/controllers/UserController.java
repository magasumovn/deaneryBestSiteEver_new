package magasumovn.deanery.controllers;

import magasumovn.deanery.model.User;
import magasumovn.deanery.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping
    public boolean create(
            @RequestBody User user
    ) {
        return userService.createUser(user);

    }
}
