package my.app.webapp.repository;

import lombok.Getter;
import my.app.webapp.model.Reader;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Repository
public class ReaderRepository {

    // GET all readers.
    private final List<Reader> readers;

    public ReaderRepository() {
        this.readers = new ArrayList<>();
        readers.add(new Reader("Aleks"));
        readers.add(new Reader("Bob"));
        readers.add(new Reader("Charlie"));
        readers.add(new Reader("David"));
        readers.add(new Reader("Ellie"));
        readers.add(new Reader("Fred"));
        readers.add(new Reader("George"));
        readers.add(new Reader("Harry"));
        readers.add(new Reader("Jack"));
        readers.add(new Reader("Kate"));
    }

    // POST new reader.
    public Reader addReader(String name) {
        if (getReaderByName(name) == null) {
            readers.add(new Reader(name));
            return getReaderByName(name);
        }
        return null;
    }

    // GET reader by id.
    public Reader getReaderById(long id) {
        return readers
                .stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // GET reader by name.
    public Reader getReaderByName(String name) {
        return readers
                .stream()
                .filter(r -> r.getName() == name)
                .findFirst()
                .orElse(null);
    }

    // DELETE reader by id.
    public Reader deleteReaderById(long id) {
        Reader reader = getReaderById(id);
        if (reader != null) {
            readers.remove(reader);
            return reader;
        }
        return null;
    }
}
