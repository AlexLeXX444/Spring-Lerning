package my.app.homework.web;

import lombok.RequiredArgsConstructor;
import my.app.homework.component.IssueWebConverter;
import my.app.homework.dto.IssueWebDto;
import my.app.homework.model.Issue;
import my.app.homework.model.Reader;
import my.app.homework.service.IssueService;
import my.app.homework.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderWebController {

    private final ReaderService readerService;
    private final IssueWebConverter issueWebConverter;
    private final IssueService issueService;

    @GetMapping
    public String readers(Model model) {
        model.addAttribute("readers", readerService.getAll());
        return "readers";
    }

    @GetMapping("/{id}")
    public String readerIssues(Model model, @PathVariable Long id) {
        List<Issue> issues = new ArrayList<>();
        Reader reader = readerService.getById(id);
        if (reader == null) {
            reader = new Reader();
            reader.setFirstName("404");
            reader.setLastName("Not Found");
        } else {
            issues = issueService.getOpenByReader(reader);
        }

        List<IssueWebDto> issuesDto = new ArrayList<>();
        if (issues != null && !issues.isEmpty()) {
            issuesDto = issues.stream().map(issueWebConverter::toDto).toList();
        }

        model.addAttribute("issues", issuesDto);
        model.addAttribute("readerName", reader.getFirstName() + " " + reader.getLastName());
        return "reader";
    }
}
