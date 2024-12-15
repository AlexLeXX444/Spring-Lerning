package my.app.vaadinlibrary.service;

import lombok.RequiredArgsConstructor;
import my.app.vaadinlibrary.model.Book;
import my.app.vaadinlibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Optional<Book> save(Book book) {
        if (!bookRepository.existsByNameAndAuthor(book.getName(), book.getAuthor())) {
            return Optional.of(bookRepository.save(book));
        }
        return Optional.empty();
    }

    public Optional<Book> update(Book book) {
        if (!bookRepository.existsByNameAndAuthorAndCount(book.getName(), book.getAuthor(), book.getCount())) {
            return Optional.of(bookRepository.save(book));
        }
        return Optional.empty();
    }

    public Optional<Book> getById(long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> deleteById(long id) {
        if (bookRepository.existsById(id)) {
            Book book = bookRepository.findById(id).get();
            bookRepository.delete(book);
            return Optional.of(book);
        }
        return Optional.empty();
    }
}
