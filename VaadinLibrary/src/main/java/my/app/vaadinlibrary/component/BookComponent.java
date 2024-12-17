package my.app.vaadinlibrary.component;

import lombok.RequiredArgsConstructor;
import my.app.vaadinlibrary.model.Book;
import my.app.vaadinlibrary.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookComponent implements CommandLineRunner {

    private final BookService bookService;


    @Override
    public void run(String... args) throws Exception {
        fillBooks();
    }

    private void fillBooks() {
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
}
