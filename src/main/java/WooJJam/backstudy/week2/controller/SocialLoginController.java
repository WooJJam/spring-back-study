package WooJJam.backstudy.week2.controller;

import WooJJam.backstudy.week2.service.SocialLoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/api")
@Controller
public class SocialLoginController {

    @Value("${kakao.redirect.uri}")
    private String KAKAO_REDIRECT_URI;

    @Value("${kakao.rest.api.key}")
    private String KAKAO_REST_API_KEY;

    private final SocialLoginService socialLoginService;

    @Autowired
    public SocialLoginController(SocialLoginService socialLoginService) {
        this.socialLoginService = socialLoginService;
    }

    @GetMapping("/auth/kakao/callback")
    @CrossOrigin
    public ModelAndView kakaoGetToken(@RequestParam String code, ModelAndView modelAndView) throws Exception {
        String token = this.socialLoginService.kakaoGetToken(code);
        String email = this.socialLoginService.getUserInfo(token);
        modelAndView.addObject("email",email);
        modelAndView.setViewName("week2/main.html");
        return modelAndView;
    }

    @GetMapping("/auth/login/kakao")
    public ModelAndView renderKakaoLoginView(HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.addObject("rest_api_key", KAKAO_REST_API_KEY);
        modelAndView.addObject("redirect_uri", KAKAO_REDIRECT_URI);
        modelAndView.setViewName("week2/kakao_login");
        return modelAndView;
    }

}
