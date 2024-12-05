package my.app.litehomework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "issue")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private  long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @Column(name = "issue_start_date")
    private LocalDateTime startDate = LocalDateTime.now();

    @Column(name = "issue_end_date")
    private LocalDateTime endDate = null;

    public Issue (Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
    }
}
