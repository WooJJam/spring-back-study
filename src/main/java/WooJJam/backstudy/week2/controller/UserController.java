package WooJJam.backstudy.week2.controller;

import WooJJam.backstudy.dtos.UserDto;
import WooJJam.backstudy.week2.service.UserService;
import WooJJam.backstudy.week2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
//        httpHeaders.get("Authorization");
        return userService.login(userDto);
    }

    @PostMapping("/login/jwtVerify")
    @ResponseBody
    public ResponseEntity<String> jwtVerify(@RequestHeader("Authorization") String authorization) {
        String accessToken = authorization.split(" ")[1];
        return userService.jwtVerify(accessToken);
    }
}
