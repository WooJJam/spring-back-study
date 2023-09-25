package WooJJam.backstudy.week2.controller;

import WooJJam.backstudy.week2.service.SessionService;
import WooJJam.backstudy.week2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/week2/v1")
public class SessionController {
    private final SessionService sessionService;
    private final UserService userService;

    @Autowired
    public SessionController(SessionService sessionService,
                             UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @PostMapping("/session/login")
    public String sessionLogin(HttpServletRequest request,
                               @RequestBody Map<String, String> user,
                               HttpServletResponse response,
                               HttpSession session) {
        String email = user.get("email");
        String password = user.get("password");
        if (this.userService.userIsEquals(email, password)) {
             /*
             false : Session이 있으면 가져오고 없으면 null return
            true : Session이 있으면 가져오고 없으면 Session을 생성해서 return (default = true)
            */
            session = request.getSession(true);
            session.setAttribute("email",email);
            session.setMaxInactiveInterval(1800);
            System.out.println("request.getHeader(\"Cookie\") = " + request.getHeader("Cookie"));
            return "Login Complete!";
        } else {
            return "Not Exist User!";
        }
    }

    @PostMapping("/session/verify")
    public String checkSession(@SessionAttribute(name="email", required = false) String email, HttpServletRequest request) {
        System.out.println("request.getRequestedSessionId() = " + request.getRequestedSessionId());
        System.out.println("request.getHeader(\"Cookie\") = " + request.getHeader("Cookie"));

       if(email == null) {
           return "Login please";
       }else {
           return email;
       }
    }
}
