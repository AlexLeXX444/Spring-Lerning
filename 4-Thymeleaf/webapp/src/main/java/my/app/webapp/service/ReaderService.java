package my.app.webapp.service;

import lombok.RequiredArgsConstructor;
import my.app.webapp.model.Reader;
import my.app.webapp.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;

    public Reader addReader(Reader reader) {
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
