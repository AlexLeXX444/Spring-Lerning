package my.app.homework.service;

import lombok.RequiredArgsConstructor;
import my.app.homework.model.Reader;
import my.app.homework.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;

    public Reader save(Reader reader) {
        return readerRepository.addReader(reader.getName());
    }

    public Reader getReaderById(long readerId) {
        return readerRepository.getReaderById(readerId);
    }

    public Reader getReaderByName(String readerName) {
        return readerRepository.getReaderByName(readerName);
    }

    public List<Reader> getAllReaders() {
        return readerRepository.getReaders();
    }

    public Reader deleteReader(long readerId) {
        return readerRepository.deleteReaderById(readerId);
    }
}
