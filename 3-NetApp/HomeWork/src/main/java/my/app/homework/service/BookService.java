package my.app.homework.service;

import lombok.RequiredArgsConstructor;
import my.app.homework.model.Book;
import my.app.homework.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book save(String bookName) {
        return bookRepository.addBook(bookName);
    }

    public Book getBookById(long bookId) {
        return bookRepository.getBookById(bookId);
    }

    public Book getBookByName(String bookName) {
        return bookRepository.getBookByName(bookName);
    }

    public List<Book> getAllBooks() {
        return bookRepository.getBooks();
    }

    public Book deleteBook(long bookId) {
        return bookRepository.deleteBookById(bookId);
    }
}
