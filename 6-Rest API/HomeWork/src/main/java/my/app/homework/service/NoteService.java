package my.app.homework.service;

import lombok.RequiredArgsConstructor;
import my.app.homework.model.Note;
import my.app.homework.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService implements CrudOptions<Note> {

    private final NoteRepository noteRepository;

    @Override
    public Note create(Note entity) {
        if (this.readByTitle(entity.getTitle()) == null) {
            return noteRepository.save(entity);
        }
        return null;
    }

    @Override
    public Note readById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public Note readByTitle(String title) {
        return noteRepository.findByTitle(title);
    }

    @Override
    public List<Note> readAll() {
        if (noteRepository.count() > 0) {
            return noteRepository.findAll();
        }
        return null;
    }

    @Override
    public Note update(Note entity) {
        if (noteRepository.existsById(entity.getId())) {
            return noteRepository.save(entity);
        }
        return null;
    }

    @Override
    public Note deleteById(Long id) {
        if (noteRepository.existsById(id)) {
            Note note = noteRepository.findById(id).orElse(null);
            noteRepository.deleteById(id);
            return note;
        }
        return null;
    }
}
