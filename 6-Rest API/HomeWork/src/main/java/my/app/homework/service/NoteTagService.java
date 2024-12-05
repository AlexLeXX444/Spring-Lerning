package my.app.homework.service;

import lombok.RequiredArgsConstructor;
import my.app.homework.model.NoteTag;
import my.app.homework.repository.NoteTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteTagService implements CrudOptions<NoteTag>{

    private final NoteTagRepository noteTagRepository;

    @Override
    public NoteTag create(NoteTag entity) {
        if (!noteTagRepository.existsById(entity.getId())) {
            return noteTagRepository.save(entity);
        }
        return null;
    }

    @Override
    public NoteTag readById(Long id) {
        return noteTagRepository.findById(id).orElse(null);
    }

    @Override
    public List<NoteTag> readAll() {
        if (noteTagRepository.count() > 0) {
            return noteTagRepository.findAll();
        }
        return null;
    }

    @Override
    public NoteTag update(NoteTag entity) {
        if (!noteTagRepository.existsById(entity.getId())) {
            return noteTagRepository.save(entity);
        }
        return null;
    }

    @Override
    public NoteTag deleteById(Long id) {
        if (!noteTagRepository.existsById(id)) {
            NoteTag entity = noteTagRepository.findById(id).orElse(null);
            noteTagRepository.deleteById(id);
            return entity;
        }
        return null;
    }

    public boolean existsByDescription(String description) {
        return noteTagRepository.existsByDescription(description);
    }

    public NoteTag findByDescription(String description) {
        return noteTagRepository.findByDescription(description);
    }
}
