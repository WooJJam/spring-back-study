package WooJJam.backstudy.week2.controller;

import WooJJam.backstudy.week2.Repository.UserRepository;
import WooJJam.backstudy.week2.Service.UserService;
import WooJJam.backstudy.week2.entity.User;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/week2/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseBody
    public String userSave(@RequestBody User user) {
        if (this.userService.findByEmailIsEquals(user.getEmail())) {
            return "Already Register!!";
        }else {
            this.userService.save(user);
            return "Complete Register!!";
        }
    }
}
