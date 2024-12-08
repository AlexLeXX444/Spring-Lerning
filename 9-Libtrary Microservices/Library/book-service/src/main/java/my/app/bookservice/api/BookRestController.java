package my.app.bookservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import my.app.bookservice.model.BookModel;
import my.app.bookservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @Operation(
            summary = "Создание новой книги.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно добавили книгу", content = @Content(schema = @Schema(implementation = BookModel.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<BookModel> addBook(
            @RequestParam String name,
            @RequestParam String author,
            @RequestParam int count) {

        BookModel book = new BookModel(name, author, count);

        if (bookService.save(book) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.getByNameAndAuthor(name, author));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Изменение названия книги по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно изменили имя книги", content = @Content(schema = @Schema(implementation = BookModel.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content)
            }
    )
    @PostMapping("/{id}/name")
    public ResponseEntity<BookModel> updateBookName(
            @PathVariable long id,
            @RequestParam String name) {
        if (bookService.findById(id) != null) {
            BookModel book = new BookModel(bookService.findById(id));
            book.setName(name);
            if (bookService.update(book) != null) {
                return ResponseEntity.status(HttpStatus.OK).body(book);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Изменение автора книги по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно изменили автора книги", content = @Content(schema = @Schema(implementation = BookModel.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content)
            }
    )
    @PostMapping("/{id}/author")
    public ResponseEntity<BookModel> updateBookAuthor(
            @PathVariable long id,
            @RequestParam String author) {
        if (bookService.findById(id) != null) {
            BookModel book = new BookModel(bookService.findById(id));
            book.setAuthor(author);
            if (bookService.update(book) != null) {
                return ResponseEntity.status(HttpStatus.OK).body(book);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @Operation(
            summary = "Изменение количества экземпляров книги по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно изменили количества экземпляров книги", content = @Content(schema = @Schema(implementation = BookModel.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content)
            }
    )
    @PostMapping("/{id}/count")
    public ResponseEntity<BookModel> updateBookCount(
            @PathVariable long id,
            @RequestParam int count) {
        if (bookService.findById(id) != null) {
            BookModel book = new BookModel(bookService.findById(id));
            book.setCount(count);
            if (bookService.update(book) != null) {
                return ResponseEntity.status(HttpStatus.OK).body(book);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получение данных книги по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получили данные книги", content = @Content(schema = @Schema(implementation = BookModel.class))),
                    @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<BookModel> findById(@PathVariable long id) {
        BookModel book = bookService.findById(id);
        if (book != null) {
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получение списка всех книг.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получили список книг", content = @Content(schema = @Schema(implementation = BookModel.class))),
                    @ApiResponse(responseCode = "404", description = "Книги не найдены", content = @Content)
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<BookModel>> findAll() {
        if (bookService.isExists()) {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.findAll());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Удаление книги по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно удалили книгу", content = @Content(schema = @Schema(implementation = BookModel.class))),
                    @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<BookModel> deleteBook(@PathVariable long id) {
        BookModel book = new BookModel(bookService.findById(id));
        if (bookService.deleteById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
