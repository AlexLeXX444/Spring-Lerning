package my.app.vaadinlibrary.repository;

import my.app.vaadinlibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByNameAndAuthor(String name, String author);
    boolean existsByNameAndAuthorAndCount(String name, String author, int count);

    long countByCount(int count);
}
