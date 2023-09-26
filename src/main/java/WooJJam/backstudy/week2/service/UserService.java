package WooJJam.backstudy.week2.service;

import WooJJam.backstudy.dtos.TokenDto;
import WooJJam.backstudy.dtos.UserDto;
import WooJJam.backstudy.repository.UserRepository;
import WooJJam.backstudy.utils.JwtUtil;
import WooJJam.backstudy.week2.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
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
    public Boolean userIsEquals(String email, String password) {
        User findUser = this.userRepository.findByEmail(email);
        if(findUser !=null && findUser.getPassword().equals(password)) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean findByEmailIsEquals(String email) {
        return this.userRepository.findByEmail(email) != null
                && this.userRepository.findByEmail(email).getEmail().equals(email);
    }

    public ResponseEntity<String> jwtVerify(String token) {
        String email = JwtUtil.getUserInfo(token, secretKey);
        return new ResponseEntity<String>(email, HttpStatus.OK);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
