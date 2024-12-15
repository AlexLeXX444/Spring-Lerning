package my.app.homework.service;

import lombok.RequiredArgsConstructor;
import my.app.homework.model.Reader;
import my.app.homework.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService implements CrudOptions<Reader> {

    private final ReaderRepository readerRepository;

    @Override
    public Reader save(Reader entity) {
        if (!readerRepository.existsByFirstNameAndLastNameAndBookAmount(entity.getFirstName(), entity.getLastName(), entity.getBookAmount())) {
            return readerRepository.save(entity);
        }
        return null;
    }

    @Override
    public Reader getById(Long id) {
        return readerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reader> getAll() {
        if (readerRepository.count() > 0) {
            return readerRepository.findAll();
        }
        return null;
    }

    @Override
    public Reader update(Reader entity) {
        if (readerRepository.existsById(entity.getId())) {
            return readerRepository.save(entity);
        }
        return null;
    }

    @Override
    public Reader deleteById(Long id) {
        if (readerRepository.existsById(id)) {
            Reader entity = readerRepository.findById(id).orElse(null);
            readerRepository.deleteById(id);
            return entity;
        }
        return null;
    }
}
