package my.app.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Для формирования представления:  книга, читатель, когда взял, когда вернул (если не вернул - пустая ячейка).
 */

@Data
@AllArgsConstructor
@Setter
public class IssueDto {

    private final long id;
    private String bookName;
    private String readerName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
