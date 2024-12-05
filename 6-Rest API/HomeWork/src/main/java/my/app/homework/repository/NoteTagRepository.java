package my.app.homework.repository;

import my.app.homework.model.NoteTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteTagRepository extends JpaRepository<NoteTag, Long> {
    boolean existsByDescription(String description);

    NoteTag findByDescription(String description);
}
