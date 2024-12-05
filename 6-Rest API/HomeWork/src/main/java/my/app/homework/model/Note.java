package my.app.homework.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private long id;

    @Column(name = "note_title")
    private String title;

    @Column(name = "note_content")
    private String content;

    @Column(name = "note_create_at")
    private LocalDateTime createAt = LocalDateTime.now();

    @Column(name = "note_delete_at")
    private LocalDateTime deleteAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "note_to_note_tag",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "note_tag_id"))
    @OrderColumn(name = "position")
    @JsonManagedReference
    private List<NoteTag> tags = new ArrayList<>();

    public Note(String title, String content, User user, List<NoteTag> tags) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.tags = tags;
    }
}
