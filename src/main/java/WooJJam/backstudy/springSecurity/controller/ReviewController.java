package WooJJam.backstudy.springSecurity.controller;

import WooJJam.backstudy.config.AuthenticationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring-security/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

//    private ReviewService reviewService;
    @PostMapping
    public ResponseEntity<String> writeReview(Authentication authentication) {
        return new ResponseEntity<String>(authentication.getName()+"님의 리뷰 등록이 완료 되었습니다.", HttpStatus.OK);
    }
}
