package my.app.litehomework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewIssueRequest {

    private long bookId;
    private long readerId;
}
