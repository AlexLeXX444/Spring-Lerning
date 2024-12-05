package my.app.litehomework.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import my.app.litehomework.model.Issue;
import my.app.litehomework.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class IssueUiController {

    private final IssueService issueService;

    @GetMapping("/issues")
    public String issuesList(Model model) {
        List<IssueDto> issues = issueService.getAll().stream().map(this::toDtoConverter).toList();
        model.addAttribute("issues", issues);
        return "issues";
    }

    private IssueDto toDtoConverter(Issue issue) {
        return new IssueDto(
                issue.getId(),
                issue.getBook().getName(),
                issue.getReader().getName(),
                issue.getStartDate(),
                issue.getEndDate());
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class IssueDto {
    private long id;
    private String bookName;
    private String readerName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
