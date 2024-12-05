package my.app.litehomework.api;

import lombok.RequiredArgsConstructor;
import my.app.litehomework.model.Book;
import my.app.litehomework.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addNewBook(@RequestBody Book book) {
        if (bookService.addBook(book) != null) {
            return ResponseEntity.ok(bookService.getByName(book.getName()));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        if (bookService.getById(id) != null) {
            return ResponseEntity.ok(bookService.getById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        if (!bookService.getAll().isEmpty()) {
            return ResponseEntity.ok(bookService.getAll());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable long id) {
        Book book = bookService.getById(id);
        if (book != null) {
            return ResponseEntity.ok().body(bookService.deleteById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
