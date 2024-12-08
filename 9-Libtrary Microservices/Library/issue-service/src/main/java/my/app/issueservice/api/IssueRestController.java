package my.app.issueservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import my.app.issueservice.model.IssueModel;
import my.app.issueservice.service.IssueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/issue")
@RequiredArgsConstructor
public class IssueRestController {

    private final IssueService issueService;

    @Operation(
            summary = "Создание новой выдачи.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно выдали книгу читателю", content = @Content(schema = @Schema(implementation = IssueModel.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<IssueModel> openIssue(
            @RequestParam long readerId,
            @RequestParam long booksIds) {
        IssueModel issue = issueService.save(readerId, booksIds);
        if (issue != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(issue);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Закрытие выдачи.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно закрыли выдачу", content = @Content(schema = @Schema(implementation = IssueModel.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content)
            }
    )
    @PostMapping("/{id}/close")
    public ResponseEntity<IssueModel> closeIssue(
            @PathVariable long id) {
        IssueModel issue = issueService.close(id);
        if (issue != null) {
            return ResponseEntity.status(HttpStatus.OK).body(issue);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Возвращает список всех выдач книг.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно вернули список выдач", content = @Content(array = @ArraySchema(schema = @Schema(implementation = IssueModel.class)))),
                    @ApiResponse(responseCode = "404", description = "Выдачи не найдены", content = @Content)
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<IssueModel>> getAllIssues() {
        List<IssueModel> issues = issueService.findAll();
        if (issues == null || issues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(issues);
    }

    @Operation(
            summary = "Возвращает список всех выдач книг.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно вернули список выдач", content = @Content(array = @ArraySchema(schema = @Schema(implementation = IssueModel.class)))),
                    @ApiResponse(responseCode = "404", description = "Выдачи не найдены", content = @Content)
            }
    )
    @GetMapping("/all/reader/{id}")
    public ResponseEntity<List<IssueModel>> getAllIssuesByReaderId(@PathVariable long id) {
        List<IssueModel> issues = issueService.findByReaderId(id);
        if (issues == null || issues.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(issues);
    }
}
