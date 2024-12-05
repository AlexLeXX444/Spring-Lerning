package my.app.homework.component;

import lombok.RequiredArgsConstructor;
import my.app.homework.dto.IssueDto;
import my.app.homework.model.Book;
import my.app.homework.model.Issue;
import my.app.homework.model.Reader;
import my.app.homework.service.BookService;
import my.app.homework.service.ReaderService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IssueConverter {

    private final BookService bookService;
    private final ReaderService readerService;

    public Issue toEntity(IssueDto issueDTO) {
        Issue issue = new Issue();
        Reader reader = readerService.getById(issueDTO.getReaderId());
        Book book = bookService.getById(issueDTO.getBookId());

        issue.setBook(book);
        issue.setReader(reader);

        return issue;
    }

    public IssueDto toDto(Issue issue) {
        IssueDto issueDto = new IssueDto();

        issueDto.setBookId(issue.getBook().getId());
        issueDto.setReaderId(issue.getReader().getId());

        return issueDto;
    }
}
