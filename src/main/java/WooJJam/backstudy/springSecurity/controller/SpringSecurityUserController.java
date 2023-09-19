package WooJJam.backstudy.springSecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring-security/v1/users")
public class SpringSecurityUserController {

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok().body("token");
    }
}
