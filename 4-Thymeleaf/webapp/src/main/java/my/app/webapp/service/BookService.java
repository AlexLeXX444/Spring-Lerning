package my.app.webapp.service;

import lombok.RequiredArgsConstructor;
import my.app.webapp.model.Book;
import my.app.webapp.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.addBook(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.getBooks();
    }

    public Book getBookById(Long id) {
        return bookRepository.getBookById(id);
    }

    public Book getBookByName(String name) {
        return bookRepository.getBookByName(name);
    }

    public Book deleteBookById(Long id) {
        return bookRepository.deleteBookById(id);
    }
}
