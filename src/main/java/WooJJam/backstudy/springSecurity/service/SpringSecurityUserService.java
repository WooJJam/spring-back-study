package WooJJam.backstudy.springSecurity.service;

import WooJJam.backstudy.dtos.TokenDto;
import WooJJam.backstudy.dtos.UserDto;
import WooJJam.backstudy.utils.JwtUtil;
import WooJJam.backstudy.repository.UserRepository;
import WooJJam.backstudy.week2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SpringSecurityUserService {

    private final UserRepository userRepository;

    @Autowired
    public SpringSecurityUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${jwt.secret}")
    private String secretKey;

    private long accessExpiredMs = 1000 * 60 * 60; // 1시간
    private long refreshExpiredMs = 1000 * 60 * 60 * 24 * 7L; // 일주일


    public ResponseEntity<String> login(UserDto userDto) {

        TokenDto tokenDto = new TokenDto();

        String email = userDto.getEmail();
        String password = userDto.getPassword();

        User userInfo = this.userRepository.findByEmail(email);
        if (userInfo != null
                && userInfo.getEmail().equals(email)
                && userInfo.getPassword().equals(password)) {
            tokenDto.setAccessToken(JwtUtil.createAccessToken(email, secretKey, accessExpiredMs));
            tokenDto.setRefreshToken(JwtUtil.createRefreshToken(email, secretKey, refreshExpiredMs));

            userRepository.updateRefreshToken(email, tokenDto.getRefreshToken());

            return new ResponseEntity<String>("AccessToken = " + tokenDto.getAccessToken() + "\n RefreshToken = " + tokenDto.getRefreshToken(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Login Failed!", HttpStatus.UNAUTHORIZED);
        }
    }

    public String getHeaders(String authorization) {
        // 인증
        return authorization;
    }

    public ResponseEntity<String> refresh(String refreshToken) {
        String email = JwtUtil.getUserInfo(refreshToken, secretKey);
        TokenDto tokenDto = new TokenDto();

        if (JwtUtil.validateToken(refreshToken, secretKey)) {
            String token = userRepository.findByEmail(email).getRefreshToken();
            if (refreshToken.equals(token)) {
                tokenDto.setAccessToken(JwtUtil.createAccessToken(email, secretKey, accessExpiredMs));
                System.out.println("tokenDto = " + tokenDto);
                return new ResponseEntity<String>("accessToken: " + tokenDto.getAccessToken(), HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Refresh Token이 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
            }
        } else {
            tokenDto.setAccessToken(JwtUtil.createAccessToken(email, secretKey, accessExpiredMs));
            tokenDto.setRefreshToken(JwtUtil.createRefreshToken(email, secretKey, refreshExpiredMs));
            userRepository.updateRefreshToken(email, tokenDto.getRefreshToken());
            return new ResponseEntity<String>("accessToken: " + tokenDto.getAccessToken() + "\n Refresh Token: " + tokenDto.getRefreshToken(), HttpStatus.OK);
        }
    }
}
