package my.app.homework.repository;

import my.app.homework.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Note findByTitle(String title);
}
