package WooJJam.backstudy.springSecurity.controller;

import WooJJam.backstudy.dtos.UserDto;
import WooJJam.backstudy.springSecurity.service.SpringSecurityUserService;
import WooJJam.backstudy.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/spring-security/v1/users")
@RequiredArgsConstructor
public class SpringSecurityUserController {

    private final SpringSecurityUserService springSecurityUserService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {

        return springSecurityUserService.login(userDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<String> refresh(@RequestHeader("RefreshToken") String refreshToken) {
        return springSecurityUserService.refresh(refreshToken);
    }

    @GetMapping("/get-header")
    public String getHeader(@RequestHeader("Authorization") String authorization) {
        return springSecurityUserService.getHeaders(authorization);
    }
}
