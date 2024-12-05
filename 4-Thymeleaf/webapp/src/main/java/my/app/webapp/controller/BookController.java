package my.app.webapp.controller;

import lombok.RequiredArgsConstructor;
import my.app.webapp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public String mainTable(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }
}
