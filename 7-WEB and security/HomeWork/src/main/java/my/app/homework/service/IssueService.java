package my.app.homework.service;

import lombok.RequiredArgsConstructor;
import my.app.homework.model.Issue;
import my.app.homework.model.Reader;
import my.app.homework.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService implements CrudOptions<Issue> {

    private final IssueRepository issueRepository;

    @Override
    public Issue save(Issue entity) {
        if (!issueRepository.existsByBookAndReader(entity.getBook(), entity.getReader())) {
            return issueRepository.save(entity);
        }
        return null;
    }

    public Issue getByBookAndReader(Issue entity) {
        return issueRepository.findByBookAndReader(entity.getBook(), entity.getReader());
    }

    public List<Issue> getOpenByReader(Reader reader) {
        List<Issue> issues = issueRepository.findAllByReader(reader);
        List<Issue> result = new ArrayList<>();

        if (!issues.isEmpty()) {
            for (Issue issue : issues) {
                if (issue.getEndDate() == null) {
                    result.add(issue);
                }
            }
            return result;
        }

        return null;
    }

    @Override
    public Issue getById(Long id) {
        return issueRepository.findById(id).orElse(null);
    }

    @Override
    public List<Issue> getAll() {
        if (issueRepository.count() > 0) {
            return issueRepository.findAll();
        }
        return null;
    }

    public List<Issue> getAllOpen() {
        List<Issue> issues = this.getAll();

        if (issues != null && !issues.isEmpty()) {
            List<Issue> openIssues = new ArrayList<>();

            for (Issue issue : issues) {
                if (issue.getEndDate() == null) {
                    openIssues.add(issue);
                }
            }

            if (!openIssues.isEmpty()) {
                return openIssues;
            }
        }

        return null;
    }

    public List<Issue> getAllClosed() {
        List<Issue> issues = this.getAll();

        if (issues != null && !issues.isEmpty()) {
            List<Issue> closedIssues = new ArrayList<>();

            for (Issue issue : issues) {
                if (issue.getEndDate() != null) {
                    closedIssues.add(issue);
                }
            }

            if (!closedIssues.isEmpty()) {
                return closedIssues;
            }
        }

        return null;
    }

    @Override
    public Issue update(Issue entity) {
        if (issueRepository.existsById(entity.getId())) {
            return issueRepository.save(entity);
        }
        return null;
    }

    @Override
    public Issue deleteById(Long id) {
        if (issueRepository.existsById(id)) {
            Issue entity = issueRepository.findById(id).orElse(null);
            issueRepository.deleteById(entity.getId());
            return entity;
        }
        return null;
    }

    public Issue updateEndDate(Issue entity) {
        Issue issue = issueRepository.findByBookAndReader(entity.getBook(), entity.getReader());

        if (issue != null && issue.getEndDate() == null) {
            issue.setEndDate(LocalDateTime.now());
            return issueRepository.save(issue);
        }

        return null;
    }


}
