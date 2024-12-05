package my.app.homework.repository;

import lombok.Getter;
import my.app.homework.model.Issue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Repository
public class IssueRepository {

    // GET all issues.
    private final List<Issue> issues;

    public IssueRepository() {
        this.issues = new ArrayList<>();
        issues.add(new Issue(1, 1));
        issues.add(new Issue(2, 1));
        issues.add(new Issue(3, 1));
        issues.add(new Issue(1, 4));
        issues.add(new Issue(1, 5));
        issues.add(new Issue(1, 6));
        issues.add(new Issue(5, 5));
        issues.add(new Issue(7, 2));
        issues.add(new Issue(8, 8));
        issues.add(new Issue(9, 1));
    }

    // POST new issue.
    public Issue addIssue(Issue issue) {
        if (issues.contains(issue)) {
            return null;
        }
        issues.add(issue);
        return getIssueById(issue.getId());
    }

    // GET issue by id.
    public Issue getIssueById(long id) {
        return issues
                .stream()
                .filter(is -> Objects.equals(is.getId(), id))
                .findFirst()
                .orElse(null);
    }

    // GET issie by book id.
    public Issue getIssueByBookId(long bookId) {
        return issues
                .stream()
                .filter(is -> Objects.equals(is.getId(), bookId))
                .findFirst()
                .orElse(null);
    }

    // GET issue by reader id.
    public Issue getIssueByReaderId(long readerId) {
        return issues
                .stream()
                .filter(is -> Objects.equals(is.getReaderId(), readerId))
                .findFirst()
                .orElse(null);
    }

    // GET issues by book id.
    public List<Issue> getIssuesByBookId(long bookId) {
        return issues
                .stream()
                .filter(is -> Objects.equals(is.getBookId(), bookId))
                .toList();
    }

    // GET issues by reader id.
    public List<Issue> getIssuesByReaderId(long readerId) {
        return issues
                .stream()
                .filter(is -> Objects.equals(is.getReaderId(), readerId))
                .toList();
    }

    // DELETE issue by id.
    public Issue deleteIssueById(long id) {
        if (getIssueById(id) != null) {
            Issue issue = getIssueById(id);
            issues.remove(issue);
            return issue;
        }
        return null;
    }
}
