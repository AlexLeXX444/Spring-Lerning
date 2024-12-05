package my.app.litehomework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reader")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reader_id")
    private long id;

    @Column(name = "reader_name")
    private String name;

    public Reader(String name) {
        this.name = name;
    }
}
