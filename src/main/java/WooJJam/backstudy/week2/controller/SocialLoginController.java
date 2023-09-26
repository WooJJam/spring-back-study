package WooJJam.backstudy.week2.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/api")
@Controller
public class SocialLoginController {

    @Value("${kakao.redirect.uri}")
    private String KAKAO_REDIRECT_URI;

    @Value("${kakao.rest.api.key}")
    private String KAKAO_REST_API_KEY;
    @ResponseBody
    @GetMapping("/auth/kakao/callback")
    public String kakaoLogin(@RequestParam String code) {
        System.out.println("code = " + code);
        return code;
    }

    @GetMapping("/auth/login/kakao")
    public ModelAndView renderKakaoLoginView(HttpServletRequest request, ModelAndView modelAndView) {
        modelAndView.addObject("rest_api_key",KAKAO_REST_API_KEY);
        modelAndView.addObject("redirect_uri",KAKAO_REDIRECT_URI);
        modelAndView.setViewName("week2/kakao_login");
        return modelAndView;
    }
}
