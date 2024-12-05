package my.app.webapp.service;

import lombok.RequiredArgsConstructor;
import my.app.webapp.dto.IssueDto;
import my.app.webapp.model.Issue;
import my.app.webapp.repository.IssueRepository;
import my.app.webapp.service.convertor.IssueConvertorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueConvertorService issueConvertorService;

    public Issue addIssue(Issue issue) {
        return issueRepository.addIssue(issue);
    }

    public Issue getIssueById(long id) {
        return issueRepository.getIssueById(id);
    }

    public List<Issue> getAllIssues() {
        return issueRepository.getIssues();
    }

    public List<IssueDto> getAllIssuesDto() {
        return issueConvertorService.toDtoList(issueRepository.getIssues());
    }

    public List<IssueDto> getIssuesDtoFromIssueList(List<Issue> issueList) {
        return issueConvertorService.toDtoList(issueList);
    }

    public List<Issue> getIssuesByBookId(long bookId) {
        return issueRepository.getIssuesByBookId(bookId);
    }

    public List<Issue> getIssuesByReaderId(long readerId) {
        return issueRepository.getIssuesByReaderId(readerId);
    }

    public Issue updateIssue(Issue issue) {
        return issueRepository.updateIssue(issue);
    }

    public Issue deleteIssueById(long id) {
        return issueRepository.deleteIssueById(id);
    }
}
