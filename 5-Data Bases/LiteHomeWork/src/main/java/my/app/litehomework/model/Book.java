package my.app.litehomework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;

    @Column(name = "book_name")
    private String name;

    @Column(name = "book_amount")
    private int amount;

    public Book(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }
}
