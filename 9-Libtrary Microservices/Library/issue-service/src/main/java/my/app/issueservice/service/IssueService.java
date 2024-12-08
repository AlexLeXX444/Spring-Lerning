package my.app.issueservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.app.issueservice.model.IssueModel;
import my.app.issueservice.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final ReaderService readerService;
    private final BookService bookService;


    @Transactional
    public IssueModel save(long readerId, long bookId) {
        if (!readerService.isExistsById(readerId)) {
            return null;
        }

        if (!bookService.isExistsById(bookId)) {
            return null;
        }

        if (!issueRepository.existsByReaderIdAndBookId(readerId, bookId)) {
            boolean isEnable = true;
            IssueModel issueModel = new IssueModel();
            issueModel.setReaderId(readerId);
            issueModel.setBookId(bookId);

            int readerBookAmount = readerService.getBookAmount(readerId);
            if (readerBookAmount > 0) {
                readerService.updateBookAmount(readerId, readerBookAmount - 1);
            } else {
                isEnable = false;
            }

            int bookCount = bookService.getBookCount(readerId);
            if (bookCount > 0) {
                bookService.updateBookCount(readerId, bookCount - 1);
            } else {
                isEnable = false;
            }

            if (isEnable) {
                return issueRepository.save(issueModel);
            }
            return null;
        }
        return null;
    }

    public IssueModel close(long id) {
        IssueModel issueModel = issueRepository.findById(id).orElse(null);

        if (issueModel != null && issueModel.getIssueEndDate() == null) {
            issueModel.setIssueEndDate(LocalDate.now());

            int readerBookAmount = readerService.getBookAmount(issueModel.getBookId());
            readerService.updateBookAmount(issueModel.getBookId(), readerBookAmount + 1);

            int bookCount = bookService.getBookCount(issueModel.getReaderId());
            bookService.updateBookCount(issueModel.getReaderId(), bookCount + 1);

            return issueRepository.save(issueModel);
        }

        return null;
    }

    public List<IssueModel> findAll() {
        List<IssueModel> issueModels = issueRepository.findAll();
        if (issueModels.isEmpty()) {
            return null;
        }
        return issueModels;
    }

    public List<IssueModel> findByReaderId(long readerId) {
        List<IssueModel> issueModels = issueRepository.findByReaderId(readerId);
        if (issueModels.isEmpty()) {
            return null;
        }
        return issueModels;
    }
}
