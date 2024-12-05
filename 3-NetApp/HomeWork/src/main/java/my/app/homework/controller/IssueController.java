package my.app.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.app.homework.model.Issue;
import my.app.homework.service.IssueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<Issue> createIssue(@RequestBody Issue issue) {
        if (issueService.findByBookId(issue.getBookId()) == null && issueService.findByReaderId(issue.getReaderId()) == null) {
            log.info("issueTimestamp={} >> Выдана книга: issueId={}, issueBookId={}, issueReaderId={}",
                    issue.getTimestamp(),
                    issue.getId(),
                    issue.getBookId(),
                    issue.getReaderId());
            return ResponseEntity.ok().body(issueService.save(issue));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(issue);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Issue>> getAllIssues() {
        if (issueService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(issueService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        if (issueService.findByBookId(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(issueService.findByBookId(id));
    }

    @GetMapping("/books")
    public ResponseEntity<List<Issue>> getByBookId(@RequestParam long bookId) {
        List<Issue> issues = issueService.findListByBookId(bookId);
        if (issues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/readers")
    public ResponseEntity<List<Issue>> getByReaderId(@RequestParam long readerId) {
        List<Issue> issues = issueService.findListByReaderId(readerId);
        if (issues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(issues);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Issue> deleteIssueById(@PathVariable Long id) {
        Issue issue = issueService.deleteById(id);
        if (issue == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("issueTimestamp={} >> Книгу вернули: issueId={}, issueBookId={}, issueReaderId={}",
                localDateTime,
                issue.getId(),
                issue.getBookId(),
                issue.getReaderId());
        return ResponseEntity.ok(issue);
    }
}
