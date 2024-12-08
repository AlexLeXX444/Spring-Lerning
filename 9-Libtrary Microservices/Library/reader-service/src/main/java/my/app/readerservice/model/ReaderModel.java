package my.app.readerservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "readers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first-name")
    private String firstName;

    @Column(name = "last-name")
    private String lastName;

    @Column(name = "book-amount")
    private int bookAmount;

    public ReaderModel(String firstName, String lastName, int bookAmount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookAmount = bookAmount;
    }

    public ReaderModel(ReaderModel reader) {
        this.id = reader.getId();
        this.firstName = reader.getFirstName();
        this.lastName = reader.getLastName();
        this.bookAmount = reader.getBookAmount();
    }
}
