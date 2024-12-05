package my.app.webapp.service.convertor;

import lombok.RequiredArgsConstructor;
import my.app.webapp.dto.IssueDto;
import my.app.webapp.model.Issue;
import my.app.webapp.repository.BookRepository;
import my.app.webapp.repository.IssueRepository;
import my.app.webapp.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueConvertorService {

    private final IssueRepository issueRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    // CONVERT Issue Entity to Issue DTO.
    public IssueDto toDto(Issue issue) {
        return new IssueDto(
                issue.getId(),
                bookRepository.getBookById(issue.getBookId()).getName(),
                readerRepository.getReaderById(issue.getReaderId()).getName(),
                issue.getStartDate(),
                issue.getEndDate()
                );
    }

    // CONVERT Issue DTO to Issue Entity.
    public Issue toEntity(IssueDto dto) {
        return new Issue(
                dto.getId(),
                bookRepository.getBookByName(dto.getBookName()).getId(),
                readerRepository.getReaderByName(dto.getReaderName()).getId(),
                dto.getStartDate(),
                dto.getEndDate()
        );
    }

    // CONVERT list of Issue DTO to list of Issue Entity.
    public List<IssueDto> toDtoList(List<Issue> issues) {
        if (issues.isEmpty()) {
            return new ArrayList<>();
        }
        return issues.stream().map(this::toDto).toList();
    }

    // CONVERT list of Issue Entity to list of Issue DTO.
    public List<Issue> toEntityList(List<IssueDto> dtos) {
        if (dtos.isEmpty()) {
            return new ArrayList<>();
        }
        return dtos.stream().map(this::toEntity).toList();
    }
}
