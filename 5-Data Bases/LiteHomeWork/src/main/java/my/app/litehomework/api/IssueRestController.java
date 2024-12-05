package my.app.litehomework.api;

import lombok.RequiredArgsConstructor;
import my.app.litehomework.dto.NewIssueRequest;
import my.app.litehomework.model.Book;
import my.app.litehomework.model.Issue;
import my.app.litehomework.model.Reader;
import my.app.litehomework.service.BookService;
import my.app.litehomework.service.IssueService;
import my.app.litehomework.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issue")
@RequiredArgsConstructor
public class IssueRestController {

    private final IssueService issueService;
    private final BookService bookService;
    private final ReaderService readerService;

    @PostMapping
    public ResponseEntity<Issue> addNewIssue(@RequestBody NewIssueRequest request) {
        Book book = bookService.getById(request.getBookId());
        Reader reader = readerService.getById(request.getReaderId());

        if (issueService.existByBookAndReader(book, reader)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if (book == null || reader == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Issue issue = issueService.addIssue(book, reader);
        return ResponseEntity.ok(issue);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssue(@PathVariable long id) {
        Issue issue = issueService.getById(id);
        if (issue == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(issue);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Issue>> getAllIssues() {
        List<Issue> issues = issueService.getAll();
        if (issues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(issues);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Issue> deleteIssue(@PathVariable long id) {
        Issue issue = issueService.getById(id);
        if (issue == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(issueService.deleteById(id));
    }

    @GetMapping("/book")
    public ResponseEntity<List<Issue>> getIssuesByBookId(@RequestParam long bookId) {
        List<Issue> issues = issueService.getByBook(bookService.getById(bookId));
        if (issues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(issues);
    }

    @GetMapping("/reader")
    public ResponseEntity<List<Issue>> getIssuesByReaderId(@RequestParam long readerId) {
        List<Issue> issues = issueService.getByReader(readerService.getById(readerId));
        if (issues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(issues);
    }
}
