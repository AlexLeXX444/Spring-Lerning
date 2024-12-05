package my.app.homework.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "note_tags")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_tag_id")
    private long id;

    @Column(name = "note_tag_description")
    private String description;

    @Column(name = "note_tag_color")
    private String color;

    @ManyToMany(mappedBy = "tags")
    @JsonBackReference
    private List<Note> notes = new ArrayList<>();

    public NoteTag(String description, String color) {
        this.description = description;
        this.color = color;
    }
}
