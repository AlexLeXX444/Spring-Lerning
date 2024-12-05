package my.app.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueWebDto {
    private long id;
    private String bookName;
    private LocalDateTime startDate;
}
