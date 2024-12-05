package my.app.litehomework.service;

import lombok.RequiredArgsConstructor;

import my.app.litehomework.model.Book;
import my.app.litehomework.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book addBook(Book book) {
        if (!bookRepository.existsByName(book.getName())) {
            return bookRepository.save(book);
        }
        return null;
    }

    public Book getById(long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookOptional.orElse(null);
    }

    public Book getByName(String name) {
        return bookRepository.findByName(name);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book deleteById(long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            bookRepository.deleteById(id);
        }
        return bookOptional.orElse(null);
    }
}
