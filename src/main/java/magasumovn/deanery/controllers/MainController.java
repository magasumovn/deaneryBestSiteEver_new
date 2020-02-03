package magasumovn.deanery.controllers;

import magasumovn.deanery.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        if (user != null) {
            model.addAttribute("profile", user);
        }

        return "index";
    }
}
