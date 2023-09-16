package WooJJam.backstudy.week2.controller;

import WooJJam.backstudy.week2.Repository.UserRepository;
import WooJJam.backstudy.week2.entity.User;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    @ResponseBody
    public void userSave(@RequestBody User user) {
        System.out.println("user = " + user);
        this.userRepository.save(user);
    }
}
