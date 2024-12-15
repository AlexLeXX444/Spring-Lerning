package my.app.homework.component;

import lombok.RequiredArgsConstructor;
import my.app.homework.model.Book;
import my.app.homework.model.Issue;
import my.app.homework.model.Reader;
import my.app.homework.model.security.Role;
import my.app.homework.model.security.User;
import my.app.homework.repository.security.RoleRepository;
import my.app.homework.repository.security.UserRepository;
import my.app.homework.service.BookService;
import my.app.homework.service.IssueService;
import my.app.homework.service.ReaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataBaseRandomDataComponent implements CommandLineRunner {

    private final BookService bookService;
    private final ReaderService readerService;
    private final IssueService issueService;

    @Lazy
    private final UserRepository userRepository;

    @Lazy
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        generateBooksIfNotExist();
        generateReadersIfNotExist();
        generateIssuesIfNotExist();

        if (!userRepository.existsByUsername("user") && !userRepository.existsByUsername("admin")) {
            createUser("user", "user", "ROLE_READER");
            createUser("admin", "admin", "ROLE_ADMIN");
        }
    }

    private void generateBooksIfNotExist() {
        List<Book> books = new ArrayList<>();

        books.add(new Book("Лев Толстой", "Война и мир", 9));
        books.add(new Book("Федор Достоевский", "Преступление и наказание", 8));
        books.add(new Book("Александр Пушкин", "Евгений Онегин", 7));
        books.add(new Book("Михаил Булгаков", "Мастер и Маргарита", 6));
        books.add(new Book("Антон Чехов", "Рассказы", 5));
        books.add(new Book("Николай Гоголь", "Мертвые души", 4));
        books.add(new Book("Иван Тургенев", "Отцы и дети", 3));
        books.add(new Book("Владимир Набоков", "Лолита", 2));
        books.add(new Book("Максим Горький", "На дне", 1));
        books.add(new Book("Сергей Есенин", "Стихотворения", 0));
        books.add(new Book("Борис Пастернак", "Доктор Живаго", 9));
        books.add(new Book("Илья Ильф и Евгений Петров", "Двенадцать стульев", 8));
        books.add(new Book("Андрей Белый", "Петербург", 7));
        books.add(new Book("Валентин Распутин", "Прощание с Матёрой", 6));
        books.add(new Book("Виктор Пелевин", "Generation 'П'", 5));
        books.add(new Book("Василий Шукшин", "Рассказы", 4));
        books.add(new Book("Леонид Андреев", "Жизнь Василия Фивейского", 3));
        books.add(new Book("Алексей Толстой", "Хождение по мукам", 2));
        books.add(new Book("Павел Бажов", "Малахитовая шкатулка", 1));
        books.add(new Book("Александр Грин", "Алые паруса", 0));

        for (Book book : books) {
            bookService.save(book);
        }
    }

    private void generateReadersIfNotExist() {
        List<Reader> readers = new ArrayList<>();

        readers.add(new Reader("Иван", "Иванов", 0));
        readers.add(new Reader("Анна", "Петрова", 1));
        readers.add(new Reader("Сергей", "Сидоров", 2));
        readers.add(new Reader("Елена", "Кузнецова", 3));
        readers.add(new Reader("Дмитрий", "Орлов", 4));
        readers.add(new Reader("Ольга", "Николаева", 5));
        readers.add(new Reader("Алексей", "Федоров", 0));
        readers.add(new Reader("Марина", "Смирнова", 1));
        readers.add(new Reader("Владимир", "Попов", 2));
        readers.add(new Reader("Татьяна", "Морозова", 3));

        for (Reader reader : readers) {
            readerService.save(reader);
        }
    }

    private void generateIssuesIfNotExist() {
        List<Book> books = bookService.getAll() != null ? bookService.getAll() : Collections.emptyList();
        List<Reader> readers = readerService.getAll() != null ? readerService.getAll() : Collections.emptyList();
        int i = issueService.getAll() != null ? issueService.getAll().size() : 0;

        while (i < 10) {
            if (issueService.save(new Issue(getRandomElement(books), getRandomElement(readers))) != null) {
                i++;
            }
        }
    }

    public static <T> T getRandomElement(List<T> list) {
        int index = new Random().nextInt(list.size());
        return list.get(index);
    }

    private void createUser(String username, String password, String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.getRoles().add(role);
        userRepository.save(user);
    }
}
