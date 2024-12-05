package my.app.webapp.controller;

import lombok.RequiredArgsConstructor;
import my.app.webapp.dto.IssueDto;
import my.app.webapp.model.Reader;
import my.app.webapp.service.IssueService;
import my.app.webapp.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;
    private final IssueService issueService;

    @GetMapping("/readers")
    public String readers(Model model) {
        model.addAttribute("readers", readerService.getAllReaders());
        return "readers";
    }

    @GetMapping("/reader/{id}")
    public String reader(Model model, @PathVariable long id) {
        Reader reader = readerService.getReaderById(id);
        List<IssueDto> issuesDto = new ArrayList<>();
        if (!issueService.getIssuesDtoFromIssueList(issueService.getIssuesByReaderId(id)).isEmpty()) {
            for (IssueDto issueDto : issueService.getIssuesDtoFromIssueList(issueService.getIssuesByReaderId(id))) {
                if (issueDto.getEndDate() == null) {
                    issuesDto.add(issueDto);
                }
            }
        }
        model.addAttribute("readerName", reader.getName());
        model.addAttribute("issues", issuesDto);
        return "reader";
    }
}
