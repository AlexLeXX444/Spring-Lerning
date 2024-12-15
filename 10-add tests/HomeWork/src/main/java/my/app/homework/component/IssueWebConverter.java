package my.app.homework.component;

import lombok.RequiredArgsConstructor;
import my.app.homework.dto.IssueWebDto;
import my.app.homework.model.Book;
import my.app.homework.model.Issue;
import my.app.homework.service.BookService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IssueWebConverter {

    private final BookService bookService;

    public IssueWebDto toDto(Issue issue) {
        IssueWebDto issueDto = new IssueWebDto();
        Book book = bookService.getById(issue.getBook().getId());

        issueDto.setId(issue.getId());
        issueDto.setBookName(book.getName());
        issueDto.setStartDate(issue.getStartDate());

        return issueDto;
    }
}
