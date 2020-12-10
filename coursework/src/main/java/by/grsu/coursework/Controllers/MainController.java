package by.grsu.coursework.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import by.grsu.coursework.security.UserAuth;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");

        /*if (UserAuth.getCurrentUsername() == "anonymousUser") {
            model.addAttribute("loginState", "Sign in");
        }
        else {
            model.addAttribute("loginState", UserAuth.getCurrentUsername());
        }*/

        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "О нас");
        return "about";
    }

    @GetMapping("/login")
    public String login(Model model) {

        if (UserAuth.getCurrentUsername() == "anonymousUser") {
            return "login";
        }
        else {
            return "redirect:/profile";
        }
    }

    @GetMapping("/profile")
    public String profile(Model model) {

        if (UserAuth.getCurrentUsername() != "anonymousUser") {
            model.addAttribute("username", UserAuth.getCurrentUsername());
            return "profile";
        }
        else {
            return "redirect:/login";
        }
    }
}