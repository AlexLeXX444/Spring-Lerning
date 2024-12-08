package my.app.issueservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDto {

    private long id;
    private String firstName;
    private String lastName;
    private int bookAmount;
}
