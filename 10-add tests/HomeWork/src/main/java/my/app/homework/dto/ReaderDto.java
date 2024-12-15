package my.app.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDto {
    private String firstName;
    private String lastName;
    private int bookAmount;
}
