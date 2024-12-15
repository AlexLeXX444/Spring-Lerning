package my.app.homework.web;

import lombok.RequiredArgsConstructor;
import my.app.homework.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookWebController {

    private final BookService bookService;

    @GetMapping
    public String getBook(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "books";
    }
}
