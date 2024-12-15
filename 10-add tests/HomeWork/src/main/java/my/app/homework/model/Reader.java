package my.app.homework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "readers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reader_id")
    private long id;

    @Column(name = "reader_first_name")
    private String firstName;

    @Column(name = "reader_last_name")
    private String lastName;

    @Column(name = "reader_book_amount")
    private int bookAmount;

    public Reader (String firstName, String lastName, int bookAmount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookAmount = bookAmount;
    }
}
