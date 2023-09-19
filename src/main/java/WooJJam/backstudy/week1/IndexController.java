package WooJJam.backstudy.week1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/week1/v1")
public class IndexController {

    private final Map<String, Object> json = new HashMap<>();
    @GetMapping("/index")
    public String renderHtml() {
        return "week1/index";
    }

    @PostMapping("/index")
    @ResponseBody
    public Map<String, Object> jsonResponse() {
        json.put("text","hi Postman");
        System.out.println(json);
        return json;
    }
}
