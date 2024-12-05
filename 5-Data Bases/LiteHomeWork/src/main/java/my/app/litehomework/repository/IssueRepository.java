package my.app.litehomework.repository;

import my.app.litehomework.model.Book;
import my.app.litehomework.model.Issue;
import my.app.litehomework.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long>{
    List<Issue> findByBook(Book book);

    List<Issue> findByReader(Reader reader);

    boolean existsByBookAndReader(Book book, Reader reader);
}
