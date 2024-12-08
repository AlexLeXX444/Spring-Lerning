package my.app.bookservice.service;

import lombok.RequiredArgsConstructor;
import my.app.bookservice.model.BookModel;
import my.app.bookservice.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookModel save(BookModel book) {
        if (!bookRepository.existsByName(book.getName())) {
            return bookRepository.save(book);
        }
        return null;
    }

    public BookModel update(BookModel book) {
        BookModel existBook = bookRepository.findById(book.getId()).orElse(null);
        if (existBook != null) {
            int counter = 0;
            if (!existBook.getName().equals(book.getName())) {
                existBook.setName(book.getName());
                counter++;
            }
            if (!existBook.getAuthor().equals(book.getAuthor())) {
                existBook.setAuthor(book.getAuthor());
                counter++;
            }
            if (existBook.getCount() != book.getCount()) {
                existBook.setCount(book.getCount());
                counter++;
            }
            if (counter > 0) {
                return bookRepository.save(existBook);
            }
        }

        return null;
    }

    public BookModel findById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public BookModel findByName(String name) {
        return bookRepository.findByName(name);
    }

    public List<BookModel> findAllByIdList(List<Long> idList) {
        List<BookModel> books = bookRepository.findAllById(idList);

        if (books.isEmpty()) {
            return null;
        }
        return books;
    }

    public List<BookModel> findAll() {
        List<BookModel> books = bookRepository.findAll();
        if (books.isEmpty()) {
            return null;
        }
        return books;
    }

    public BookModel deleteById(long id) {
        BookModel existBook = bookRepository.findById(id).orElse(null);

        if (existBook != null) {
            bookRepository.delete(existBook);
            return existBook;
        }
        return null;
    }

    public boolean isExists() {
        if (bookRepository.count() == 0) {
            return false;
        }
        return true;
    }

    public BookModel getByNameAndAuthor(String name, String author) {
        return bookRepository.findByNameAndAuthor(name, author);
    }
}
