package my.app.homework.repository;

import lombok.Getter;
import my.app.homework.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Repository
public class BookRepository {

    // GET all books.
    private final List<Book> books;

    public BookRepository() {
        books = new ArrayList<>();
        books.add(new Book("Гарри Поттер и философский камень"));
        books.add(new Book("Гарри Поттер и Тайная комната"));
        books.add(new Book("Гарри Поттер и узник Азкабана"));
        books.add(new Book("Гарри Поттер и Кубок огня"));
        books.add(new Book("Гарри Поттер и Орден Феникса"));
        books.add(new Book("Гарри Поттер и Принц-полукровка"));
        books.add(new Book("Гарри Поттер и Дары Смерти: Часть 1"));
        books.add(new Book("Гарри Поттер и Дары Смерти: Часть 2"));
        books.add(new Book("Гарри Поттер: Чемпионат мира по квиддичу"));
        books.add(new Book("Квиддич с древности до наших дней"));
    }

    // POST new book.
    public Book addBook(String bookName) {
        if (getBookByName(bookName) == null) {
            books.add(new Book(bookName));
            return getBookByName(bookName);
        }
        return null;
    }

    // GET book by id.
    public Book getBookById(long id) {
        return books
                .stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElse(null);
    }

    // GET book by name.
    public Book getBookByName(String name) {
        return books
                .stream()
                .filter(it -> Objects.equals(it.getName(), name))
                .findFirst()
                .orElse(null);
    }

    // DELETE book by id.
    public Book deleteBookById(long id) {
        if (getBookById(id) != null) {
            Book book = getBookById(id);
            books.remove(book);
            return book;
        }
        return null;
    }
}
