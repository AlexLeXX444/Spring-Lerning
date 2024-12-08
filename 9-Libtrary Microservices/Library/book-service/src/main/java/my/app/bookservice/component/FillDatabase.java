package my.app.bookservice.component;

import lombok.RequiredArgsConstructor;
import my.app.bookservice.model.BookModel;
import my.app.bookservice.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FillDatabase implements CommandLineRunner {

    private final BookService bookService;

    @Override
    public void run(String... args) throws Exception {
        fillBooks();
    }

    private void fillBooks() {
        List<BookModel> books = new ArrayList<>();

        books.add(new BookModel("Лев Толстой", "Война и мир", 9));
        books.add(new BookModel("Федор Достоевский", "Преступление и наказание", 8));
        books.add(new BookModel("Александр Пушкин", "Евгений Онегин", 7));
        books.add(new BookModel("Михаил Булгаков", "Мастер и Маргарита", 6));
        books.add(new BookModel("Антон Чехов", "Рассказы", 5));
        books.add(new BookModel("Николай Гоголь", "Мертвые души", 4));
        books.add(new BookModel("Иван Тургенев", "Отцы и дети", 3));
        books.add(new BookModel("Владимир Набоков", "Лолита", 2));
        books.add(new BookModel("Максим Горький", "На дне", 1));
        books.add(new BookModel("Сергей Есенин", "Стихотворения", 0));
        books.add(new BookModel("Борис Пастернак", "Доктор Живаго", 9));
        books.add(new BookModel("Илья Ильф и Евгений Петров", "Двенадцать стульев", 8));
        books.add(new BookModel("Андрей Белый", "Петербург", 7));
        books.add(new BookModel("Валентин Распутин", "Прощание с Матёрой", 6));
        books.add(new BookModel("Виктор Пелевин", "Generation 'П'", 5));
        books.add(new BookModel("Василий Шукшин", "Рассказы", 4));
        books.add(new BookModel("Леонид Андреев", "Жизнь Василия Фивейского", 3));
        books.add(new BookModel("Алексей Толстой", "Хождение по мукам", 2));
        books.add(new BookModel("Павел Бажов", "Малахитовая шкатулка", 1));
        books.add(new BookModel("Александр Грин", "Алые паруса", 0));

        for (BookModel book : books) {
            bookService.save(book);
        }
    }
}
