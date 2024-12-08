package my.app.issueservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "issues")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private long id;

    @Column(name = "book_ids")
    private long bookId;

    @Column(name = "reader_id")
    private long readerId;

    @Column(name = "issue_start_date")
    private LocalDate issueStartDate = LocalDate.now();

    @Column(name = "issue_end_date")
    private LocalDate issueEndDate = null;
}
