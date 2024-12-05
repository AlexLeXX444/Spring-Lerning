package my.app.litehomework.repository;

import my.app.litehomework.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    boolean existsByName(String name);

    Reader findByName(String name);
}
