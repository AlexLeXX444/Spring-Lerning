package my.app.webapp.repository;

import lombok.Getter;
import my.app.webapp.model.Book;
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
        books.add(new Book("Гарри Поттер и философский камень", 5));
        books.add(new Book("Гарри Поттер и Тайная комната", 3));
        books.add(new Book("Гарри Поттер и узник Азкабана", 3));
        books.add(new Book("Гарри Поттер и Кубок огня", 4));
        books.add(new Book("Гарри Поттер и Орден Феникса", 5));
        books.add(new Book("Гарри Поттер и Принц-полукровка", 5));
        books.add(new Book("Гарри Поттер и Дары Смерти: Часть 1", 2));
        books.add(new Book("Гарри Поттер и Дары Смерти: Часть 2", 2));
        books.add(new Book("Гарри Поттер: Чемпионат мира по квиддичу", 10));
        books.add(new Book("Квиддич с древности до наших дней", 1));
    }

    // POST new book.
    public Book addBook(Book book) {
        if (getBookByName(book.getName()) == null && book.getAmount() > 0) {
            books.add(new Book(book.getName(), book.getAmount()));
            return book;
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