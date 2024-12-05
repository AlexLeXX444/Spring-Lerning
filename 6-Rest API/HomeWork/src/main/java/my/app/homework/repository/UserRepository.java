package my.app.homework.repository;

import my.app.homework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByFirstName(String firstName);

    boolean existsByLastName(String lastName);

    User findByFirstNameAndLastName(String firstName, String lastName);
}
