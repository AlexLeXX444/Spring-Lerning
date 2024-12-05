package my.app.litehomework.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import my.app.litehomework.model.Book;
import my.app.litehomework.model.Issue;
import my.app.litehomework.model.Reader;
import my.app.litehomework.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

    public Issue addIssue(Book book, Reader reader) {
        Issue issue = new Issue();
        issue.setBook(book);
        issue.setReader(reader);
        if (!issueRepository.existsByBookAndReader(book, reader)) {
            return issueRepository.save(issue);
        }
        return null;
    }

    public boolean existByBookAndReader(Book book, Reader reader) {
        return issueRepository.existsByBookAndReader(book, reader);
    }

    public Issue getById(long id) {
        return issueRepository.findById(id).orElse(null);
    }

    public List<Issue> getByBook(Book book) {
        return issueRepository.findByBook(book);
    }

    public List<Issue> getByReader(Reader reader) {
        return issueRepository.findByReader(reader);
    }

    public List<Issue> getAll() {
        return issueRepository.findAll();
    }

    public Issue deleteById(long id) {
        Optional<Issue> issue = issueRepository.findById(id);
        if (issue.isPresent()) {
            issueRepository.deleteById(id);
            return issue.get();
        }
        return null;
    }
}
