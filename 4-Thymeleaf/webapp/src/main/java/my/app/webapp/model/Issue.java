package my.app.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Issue {

    public static long sequence = 1L;

    private final long id;
    private long bookId;
    private long readerId;

    private final LocalDateTime startDate;
    private LocalDateTime endDate;

    public Issue(long bookId, long readerId) {
        this.id = sequence++;
        this.bookId = bookId;
        this.readerId = readerId;
        this.startDate = LocalDateTime.now();
        this.endDate = null;
    }
}
