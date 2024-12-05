package my.app.litehomework.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import my.app.litehomework.model.Issue;
import my.app.litehomework.model.Reader;
import my.app.litehomework.service.IssueService;
import my.app.litehomework.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class ReaderUiController {

    private final ReaderService readerService;
    private final IssueService issueService;

    @GetMapping("/readers")
    public String readersList(Model model) {
        model.addAttribute("readersList", readerService.getAll());
        return "readers";
    }

    @GetMapping("/reader/{readerId}")
    public String issuesByReaderList(@PathVariable long readerId, Model model) {
        if (readerService.getById(readerId) != null) {
            Reader reader = readerService.getById(readerId);
            List<Issue> issues = issueService.getByReader(reader);
            List<IssueToTransfer> issueList = new ArrayList<>();
            for (IssueToTransfer issueToTransfer : issues.stream().map(this::issueToTransferConverter).toList()) {
                if (issueToTransfer.getEndDate() == null) {
                    issueList.add(issueToTransfer);
                }
            }
            model.addAttribute("issueList", issueList);
            model.addAttribute("readerName", reader.getName());
            return "reader";
        }
        model.addAttribute("readerName", "404 :: Not found");
        return "reader";
    }

    private IssueToTransfer issueToTransferConverter(Issue issue) {
        IssueToTransfer issueToTransfer = new IssueToTransfer();
        issueToTransfer.setId(issue.getId());
        issueToTransfer.setBookName(issue.getBook().getName());
        issueToTransfer.setStartDate(issue.getStartDate());
        issueToTransfer.setEndDate(issue.getEndDate());
        return issueToTransfer;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class IssueToTransfer {
    private long id;
    private String bookName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
