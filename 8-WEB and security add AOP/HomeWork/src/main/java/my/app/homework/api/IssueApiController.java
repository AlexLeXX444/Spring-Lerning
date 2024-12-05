package my.app.homework.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import my.app.homework.component.IssueConverter;
import my.app.homework.dto.IssueDto;
import my.app.homework.model.Book;
import my.app.homework.model.Issue;
import my.app.homework.model.Reader;
import my.app.homework.service.BookService;
import my.app.homework.service.IssueService;
import my.app.homework.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issue")
@RequiredArgsConstructor
public class IssueApiController {

    private final IssueService issueService;
    private final BookService bookService;
    private final ReaderService readerService;
    private final IssueConverter issueConverter;

    @Operation(
            summary = "Выдача книги читателю, открытие выдачи",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно выдано", content = @Content(schema = @Schema(implementation = Issue.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content)
            }
    )
    @PostMapping("/open")
    public ResponseEntity<Issue> openIssue(@RequestBody IssueDto issueDtoDto) {
        Book book = bookService.getById(issueDtoDto.getBookId());
        Reader reader = readerService.getById(issueDtoDto.getReaderId());

        if (book != null && reader != null && book.getCount() > 0 && reader.getBookAmount() > 0) {
            Issue issue = issueConverter.toEntity(issueDtoDto);

            if (issueService.save(issue) != null) {
                book.setCount(book.getCount() - 1);
                reader.setBookAmount(reader.getBookAmount() - 1);
                bookService.save(book);
                readerService.save(reader);
                return ResponseEntity.status(HttpStatus.CREATED).body(issue);
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Возврат книги читателем, закрытие выдачи",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно возвращена", content = @Content(schema = @Schema(implementation = Issue.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content)
            }
    )
    @PostMapping("/close")
    public ResponseEntity<Issue> closeIssue(@RequestBody IssueDto issueDto) {
        Book book = bookService.getById(issueDto.getBookId());
        Reader reader = readerService.getById(issueDto.getReaderId());

        if (book != null && reader != null) {
            Issue issue = issueConverter.toEntity(issueDto);

            if (issueService.updateEndDate(issue) != null) {
                book.setCount(book.getCount() + 1);
                reader.setBookAmount(reader.getBookAmount() + 1);
                bookService.save(book);
                readerService.save(reader);
                return ResponseEntity.status(HttpStatus.OK).body(issueService.getByBookAndReader(issue));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Получение списка всех выдач книг.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получено", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Issue.class)))),
                    @ApiResponse(responseCode = "404", description = "Выдачи не найдены", content = @Content)
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<Issue>> getAllIssues() {
        List<Issue> issues = issueService.getAll();
        if (issues != null) {
            return ResponseEntity.status(HttpStatus.OK).body(issueService.getAll());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получение списка открытых выдач книг.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получено", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Issue.class)))),
                    @ApiResponse(responseCode = "404", description = "Выдачи не найдены", content = @Content)
            }
    )
    @GetMapping("/all/open")
    public ResponseEntity<List<Issue>> getAllOpenIssues() {
        List<Issue> issues = issueService.getAllOpen();

        if (issues != null) {
            return ResponseEntity.status(HttpStatus.OK).body(issues);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получение списка закрытых выдач книг.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получено", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Issue.class)))),
                    @ApiResponse(responseCode = "404", description = "Выдачи не найдены", content = @Content)
            }
    )
    @GetMapping("/all/close")
    public ResponseEntity<List<Issue>> getAllClosedIssues() {
        List<Issue> issues = issueService.getAllClosed();

        if (issues != null) {
            return ResponseEntity.status(HttpStatus.OK).body(issues);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Внесение правок в выдачу.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно внесены правки", content = @Content(schema = @Schema(implementation = Issue.class))),
                    @ApiResponse(responseCode = "404", description = "Выдача не найдена", content = @Content)
            }
    )
    @PostMapping("/{id}")
    public ResponseEntity<Issue> updateIssue(@PathVariable("id") Long id, @RequestBody long bookId, @RequestBody long readerId) {
        Issue issue = issueService.getById(id);
        Book book = bookService.getById(bookId);
        Reader reader = readerService.getById(readerId);

        if (book != null && reader != null && issue != null) {
            issue.setBook(bookService.getById(bookId));
            issue.setReader(readerService.getById(readerId));
            return ResponseEntity.status(HttpStatus.OK).body(issueService.update(issue));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
