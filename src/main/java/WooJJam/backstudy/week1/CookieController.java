package WooJJam.backstudy.week1;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/v1/cookie")
@Controller
public class CookieController {
    @ResponseBody
    @GetMapping
    public Map<String, String> getCookies(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            map.put(cookies[i].getName(), cookies[i].getValue());
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(
            @RequestBody Map<String, String> data,
            HttpServletResponse response,
            HttpServletRequest request) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        // Json 문자열을 JsonNode로 파싱
//        JsonNode jsonNode = objectMapper.readTree(jsonData);
//
//        // Json 데이터를 동적으로 처리
        String key = data.get("key");
        String value = data.get("value");
        String message = null;
        boolean isBoolean = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie checkCookie : cookies) {
                if (checkCookie.getName().equals(key)) {
                    message = "Hi " + checkCookie.getValue();
                    return message;
                }
            }
        }
        Cookie cookie = new Cookie(key, value);
        response.addCookie(cookie);
        return message;
    }

    @ResponseBody
    @PostMapping("/modify")
    public String modify(@RequestBody Map<String, String> data,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String key = data.get("key");
        String value = data.get("value");

        Cookie cookie = new Cookie(key, value);
        response.addCookie(cookie);
        return "Modify Complete!";
    }

    @ResponseBody
    @PostMapping("/withdrawl")
    public String withdrawl(@RequestBody Map<String, String> data,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        String key = data.get("key");

        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie chekcCookie : cookies) {
                if(chekcCookie.getName().equals(key)) {
                    Cookie deleteCookie = new Cookie(key, null);
                    deleteCookie.setMaxAge(0);
                    response.addCookie(deleteCookie);
                    return "withdraw!";
                }
            }
        }
        return "Not Cookie!";
    }
}

