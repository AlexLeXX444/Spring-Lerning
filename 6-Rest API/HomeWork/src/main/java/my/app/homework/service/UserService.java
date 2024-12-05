package my.app.homework.service;

import lombok.RequiredArgsConstructor;
import my.app.homework.model.User;
import my.app.homework.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements CrudOptions<User>{

    private final UserRepository userRepository;

    @Override
    public User create(User entity) {
        if (!this.existsByName(entity.getFirstName(), entity.getLastName())) {
            return userRepository.save(entity);
        }
        return null;
    }

    @Override
    public User readById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> readAll() {
        if (userRepository.count() > 0) {
            return userRepository.findAll();
        }
        return null;
    }

    @Override
    public User update(User entity) {
        if (userRepository.existsById(entity.getId())) {
            return userRepository.save(entity);
        }
        return null;
    }

    @Override
    public User deleteById(Long id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).orElse(null);
            userRepository.deleteById(id);
            return user;
        }
        return null;
    }

    public User getByNames(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName).orElse(null);
    }

    public long count() {
        return userRepository.count();
    }

    public boolean existsByName(String firstName, String lastName) {
        return (userRepository.existsByFirstName(firstName) && userRepository.existsByLastName(lastName));
    }
}
