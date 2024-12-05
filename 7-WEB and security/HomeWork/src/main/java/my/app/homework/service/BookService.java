package my.app.homework.service;

import lombok.RequiredArgsConstructor;
import my.app.homework.model.Book;
import my.app.homework.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements CrudOptions<Book> {

    private final BookRepository bookRepository;

    @Override
    public Book save(Book entity) {
        if (!bookRepository.existsByNameAndAuthorAndCount(entity.getName(), entity.getAuthor(), entity.getCount())) {
            return bookRepository.save(entity);
        }
        return null;
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> getAll() {
        if (bookRepository.count() > 0) {
            return bookRepository.findAll();
        }
        return null;
    }

    @Override
    public Book update(Book entity) {
        if (bookRepository.existsById(entity.getId())) {
            return bookRepository.save(entity);
        }
        return null;
    }

    @Override
    public Book deleteById(Long id) {
        if (bookRepository.existsById(id)) {
            Book entity = bookRepository.findById(id).orElse(null);
            bookRepository.deleteById(id);
            return entity;
        }
        return null;
    }
}
