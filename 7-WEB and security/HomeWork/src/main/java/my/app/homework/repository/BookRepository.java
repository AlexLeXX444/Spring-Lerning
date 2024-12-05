package my.app.homework.repository;

import my.app.homework.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    boolean existsByNameAndAuthorAndCount(String name, String author, int count);
}
