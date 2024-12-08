package my.app.readerservice.repository;

import my.app.readerservice.model.ReaderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<ReaderModel, Long> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    ReaderModel findByFirstNameAndLastName(String firstName, String lastName);
}
