package my.app.litehomework.service;

import lombok.RequiredArgsConstructor;
import my.app.litehomework.model.Reader;
import my.app.litehomework.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;

    public boolean existsByName(String name) {
        return readerRepository.existsByName(name);
    }

    public Reader addReader(Reader reader) {
        if (!readerRepository.existsByName(reader.getName())) {
            return readerRepository.save(reader);
        }
        return null;
    }

    public Reader getById(long id) {
        Optional<Reader> readerOptional = readerRepository.findById(id);
        return readerOptional.orElse(null);
    }

    public Reader getByName(String name) {
        return readerRepository.findByName(name);
    }

    public List<Reader> getAll() {
        return readerRepository.findAll();
    }

    public Reader deleteById(long id) {
        Optional<Reader> readerOptional = readerRepository.findById(id);
        if (readerOptional.isPresent()) {
            readerRepository.deleteById(id);
        }
        return readerOptional.orElse(null);
    }
}
