package my.app.homework.repository;

import my.app.homework.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long>{
    boolean existsByFirstNameAndLastNameAndBookAmount(String firstName, String lastName, Integer bookAmount);
}
