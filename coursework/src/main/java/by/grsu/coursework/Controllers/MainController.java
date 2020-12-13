package by.grsu.coursework.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import by.grsu.coursework.security.User;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Main page");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About");
        return "about";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return (User.getCurrentUsername() == "anonymousUser") ? "login" : "redirect:/profile";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("title", "Profile");
        return "profile";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        /*User user = new User();
        model.addAttribute("user", user);*/

        return "registration";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(Model model) {
        model.addAttribute("title", "Access denied");
        return "accessDenied";
    }

    /*@GetMapping("/signup")
    public String signup(@RequestParam String username, @RequestParam String password, Model model) {

        repository.save(newUser);
        return "login";
    }*/


    /*@PostMapping("/registration")
    public ModelAndView registerUserAccount(
        @ModelAttribute("user") @Valid User user,
        HttpServletRequest request, Errors errors) {

        try {
            User registered = user.registerNewUserAccount(user);
        } catch (UserAlreadyExistException uaeEx) {
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        }

        return new ModelAndView("successRegister", "user", user);
    }*/


    /*@PostMapping("/sign-up")
    public String blogPostAdd(@RequestParam String username, @RequestParam String password, Model model) {
        new InMemoryUserDetailsManager(
                org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                        .username(username)
                        .password(password)
                        .roles("USER")
                        .build());
        return "redirect:/login";
    }*/
}