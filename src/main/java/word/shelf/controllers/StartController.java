package word.shelf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin("*")
public class StartController {
    @GetMapping("/app")
    public String index() {
        return "word.html";
    }
}
