package my.app.homework.controller;

import lombok.RequiredArgsConstructor;
import my.app.homework.model.Book;
import my.app.homework.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        if (bookService.getBookByName(book.getName()) == null) {
            return ResponseEntity.ok(bookService.save(book.getName()));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(book);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        if (bookService.getAllBooks().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        if (bookService.getBookById(id) != null) {
            return ResponseEntity.ok(bookService.getBookById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable long id) {
        if (bookService.getBookById(id) != null) {
            return ResponseEntity.ok(bookService.deleteBook(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
