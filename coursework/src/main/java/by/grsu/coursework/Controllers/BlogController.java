package by.grsu.coursework.Controllers;

import by.grsu.coursework.models.Post;
import by.grsu.coursework.repo.PostRepository;
import by.grsu.coursework.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("title", "Forum");
        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        model.addAttribute("title", "New article");
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, @RequestParam String author, Model model) {
        author = User.getCurrentUsername();
        Post post = new Post(title, anons, full_text, author);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogPostDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Post postData = postRepository.findById(id).orElseThrow();
        postData.setViews(postData.getViews() + 1);
        postRepository.save(postData);

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "post-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogPostDetailsEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, @RequestParam String author, Model model) {
        Post post = postRepository.findById(id).orElseThrow();

        if (User.getCurrentUsername().equals(post.getAuthor())) {
            author = User.getCurrentUsername();
            post.setTitle(title);
            post.setAnons(anons);
            post.setFull_text(full_text);
            post.setAuthor(author);
            postRepository.save(post);
        }
        else return "redirect:/accessDenied";

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();

        if (User.getCurrentUsername().equals(post.getAuthor())) {
            postRepository.delete(post);
        }
        else return "redirect:/accessDenied";

        return "redirect:/blog";
    }
    
}