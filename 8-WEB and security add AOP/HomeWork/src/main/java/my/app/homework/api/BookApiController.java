package my.app.homework.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import my.app.homework.component.BookConverter;
import my.app.homework.dto.BookDto;
import my.app.homework.model.Book;
import my.app.homework.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;
    private final BookConverter bookConverter;

    @Operation(
            summary = "Добавление новой книги.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно добавлено", content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookDto bookDto) {
        Book book = bookConverter.toEntity(bookDto);
        if (bookService.save(book) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Изменение автора книги.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно изменено", content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content)
            }
    )
    @PostMapping("/{id}/author")
    public ResponseEntity<Book> editBookAuthor(@RequestBody String bookAuthor, @PathVariable Long id) {
        Book book = bookService.getById(id);
        if (book != null && bookAuthor != null) {
            book.setAuthor(bookAuthor);
            bookService.save(book);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Изменение названия книги.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно изменено", content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content)
            }
    )
    @PostMapping("/{id}/name")
    public ResponseEntity<Book> editBookName(@RequestBody String bookName, @PathVariable Long id) {
        Book book = bookService.getById(id);
        if (book != null && bookName != null) {
            book.setName(bookName);
            bookService.save(book);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Изменение количества экземпляров книги.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно изменено", content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content)
            }
    )
    @PostMapping("/{id}/count")
    public ResponseEntity<Book> editBookCount(@RequestBody int count, @PathVariable Long id) {
        Book book = bookService.getById(id);

        if (book != null && count >= 0) {
            book.setCount(count);
            return ResponseEntity.status(HttpStatus.OK).body(bookService.save(book));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получение книги по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получено", content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getById(id);
        if (book != null) {
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получение списка всех книг.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получены", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Book.class)))),
                    @ApiResponse(responseCode = "404", description = "Книги не найдены", content = @Content)
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAll();
        if (books != null) {
            return ResponseEntity.status(HttpStatus.OK).body(books);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Удаление книги по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно удалено", content = @Content(schema = @Schema(implementation = Book.class))),
                    @ApiResponse(responseCode = "404", description = "Книга не найдена", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        Book book = bookService.getById(id);
        if (book != null) {
            bookService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
