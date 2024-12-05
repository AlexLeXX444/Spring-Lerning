package my.app.homework.component;

import my.app.homework.dto.BookDto;
import my.app.homework.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {

    public Book toEntity(BookDto dto) {
        Book book = new Book();
        book.setName(dto.getName());
        book.setAuthor(dto.getAuthor());
        book.setCount(dto.getCount());
        return book;
    }

    public BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setName(book.getName());
        dto.setAuthor(book.getAuthor());
        dto.setCount(book.getCount());
        return dto;
    }
}
