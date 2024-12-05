package my.app.litehomework.web;

import lombok.RequiredArgsConstructor;
import my.app.litehomework.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class BookUiController {

    private final BookService bookService;

    @GetMapping("/books")
    public String list(Model model) {
        model.addAttribute("allBooks", bookService.getAll());
        return "books";
    }

}
