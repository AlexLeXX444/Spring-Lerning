package my.app.homework.service;

import lombok.RequiredArgsConstructor;
import my.app.homework.model.Issue;
import my.app.homework.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

    public Issue save(Issue issue) {
        return issueRepository.addIssue(issue);
    }

    public Issue findByBookId(Long id) {
        return issueRepository.getIssueByBookId(id);
    }

    public Issue findByReaderId(Long id) {
        return issueRepository.getIssueByReaderId(id);
    }

    public List<Issue> findAll() {
        return issueRepository.getIssues();
    }

    public List<Issue> findListByBookId(long bookId) {
        return issueRepository.getIssuesByBookId(bookId);
    }

    public List<Issue> findListByReaderId(long readerId) {
        return issueRepository.getIssuesByReaderId(readerId);
    }

    public Issue deleteById(long id) {
        return issueRepository.deleteIssueById(id);
    }
}
