package my.app.webapp.controller;

import lombok.RequiredArgsConstructor;
import my.app.webapp.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/issues")
    public String issues(Model model) {
        model.addAttribute("issues", issueService.getAllIssuesDto());
        return "issues";
    }
}
