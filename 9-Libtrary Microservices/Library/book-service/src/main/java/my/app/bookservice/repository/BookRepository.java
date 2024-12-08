package my.app.bookservice.repository;

import my.app.bookservice.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long> {
    boolean existsByName(String name);

    BookModel findByName(String name);

    BookModel findByNameAndAuthor(String name, String author);
}
