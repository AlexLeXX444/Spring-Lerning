package my.app.bookservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "count")
    private int count;

    public BookModel(String author, String name, int count) {
        this.name = name;
        this.author = author;
        this.count = count;
    }

    public BookModel(BookModel newBook) {
        this.id = newBook.getId();
        this.name = newBook.getName();
        this.author = newBook.getAuthor();
        this.count = newBook.getCount();
    }
}
