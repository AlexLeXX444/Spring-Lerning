package my.app.litehomework.repository;

import my.app.litehomework.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByName(String name);

    boolean existsByName(String name);
}
