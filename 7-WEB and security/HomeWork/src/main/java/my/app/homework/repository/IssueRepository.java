package my.app.homework.repository;

import my.app.homework.model.Book;
import my.app.homework.model.Issue;
import my.app.homework.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    boolean existsByBookAndReader(Book book, Reader reader);

    Issue findByBookAndReader(Book book, Reader reader);

    List<Issue> findAllByReader(Reader reader);
}
