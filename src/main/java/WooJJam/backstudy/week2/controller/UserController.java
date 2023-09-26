package WooJJam.backstudy.week2.controller;

import WooJJam.backstudy.dtos.UserDto;
import WooJJam.backstudy.week2.service.UserService;
import WooJJam.backstudy.week2.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/week2/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseBody
    public String userSave(@RequestBody User user) {
        if (this.userService.findByEmailIsEquals(user.getEmail())) {
            return "Already Register!!";
        } else {
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
