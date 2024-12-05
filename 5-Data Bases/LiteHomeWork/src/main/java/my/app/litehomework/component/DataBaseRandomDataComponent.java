package my.app.litehomework.component;

import lombok.RequiredArgsConstructor;
import my.app.litehomework.model.Book;
import my.app.litehomework.model.Reader;
import my.app.litehomework.service.BookService;
import my.app.litehomework.service.IssueService;
import my.app.litehomework.service.ReaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataBaseRandomDataComponent implements CommandLineRunner {

    private final BookService bookService;
    private final ReaderService readerService;
    private final IssueService issueService;

    @Override
    public void run(String... args) throws Exception {
        generateBooksIfNotExist();
        generateReadersIfNotExist();
        generateIssuesIfNotExist();
    }

    private void generateBooksIfNotExist() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Тайны древнего мира", 2));
        books.add(new Book("Путешествие в неизвестность", 2));
        books.add(new Book("Закат империи", 2));
        books.add(new Book("Секреты магии", 2));
        books.add(new Book("Искусство войны", 2));
        books.add(new Book("Легенды о драконах", 2));
        books.add(new Book("Загадки времени", 2));
        books.add(new Book("Звёздные миры", 2));
        books.add(new Book("Хроники древних цивилизаций", 2));
        books.add(new Book("Призраки прошлого", 2));
        for (Book book : books) {
            bookService.addBook(book);
        }
    }

    private void generateReadersIfNotExist() {
        List<Reader> readers = new ArrayList<>();
        readers.add(new Reader("Александр"));
        readers.add(new Reader("Мария"));
        readers.add(new Reader("Иван"));
        readers.add(new Reader("Екатерина"));
        readers.add(new Reader("Дмитрий"));
        readers.add(new Reader("Анна"));
        readers.add(new Reader("Сергей"));
        readers.add(new Reader("Елизавета"));
        readers.add(new Reader("Николай"));
        readers.add(new Reader("Ольга"));
        for (Reader reader : readers) {
            readerService.addReader(reader);
        }
    }

    private void generateIssuesIfNotExist() {
        List<Book> books = bookService.getAll();
        List<Reader> readers = readerService.getAll();
        int i = issueService.getAll().size();

        while (i < 10) {
            if (issueService.addIssue(getRandomElement(books), getRandomElement(readers)) != null) {
                i++;
            }
        }
    }

    public static <T> T getRandomElement(List<T> list) {
        int index = new Random().nextInt(list.size());
        return list.get(index);
    }
}
