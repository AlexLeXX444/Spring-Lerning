package my.app.issueservice.repository;

import my.app.issueservice.model.IssueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<IssueModel, Long> {
    boolean existsByReaderIdAndBookId(long readerId, long bookId);

    List<IssueModel> findAllByIssueEndDateIsNull();

    List<IssueModel> findByReaderId(long readerId);
}
