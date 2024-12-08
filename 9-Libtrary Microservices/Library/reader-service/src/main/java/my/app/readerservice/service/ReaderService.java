package my.app.readerservice.service;

import lombok.RequiredArgsConstructor;
import my.app.readerservice.model.ReaderModel;
import my.app.readerservice.repository.ReaderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;

    public ReaderModel save(ReaderModel readerModel) {
        if (!readerRepository.existsByFirstNameAndLastName(readerModel.getFirstName(), readerModel.getLastName())) {
            return readerRepository.save(readerModel);
        }
        return null;
    }

    public ReaderModel update(ReaderModel readerModel) {
        ReaderModel updatedReaderModel = readerRepository.findById(readerModel.getId()).orElse(null);
        if (updatedReaderModel != null) {
            int counter = 0;
            if (!readerModel.getFirstName().equals(updatedReaderModel.getFirstName())) {
                counter++;
                updatedReaderModel.setFirstName(readerModel.getFirstName());
            }
            if (!readerModel.getLastName().equals(updatedReaderModel.getLastName())) {
                counter++;
                updatedReaderModel.setLastName(readerModel.getLastName());
            }
            if (updatedReaderModel.getBookAmount() != readerModel.getBookAmount()) {
                counter++;
                updatedReaderModel.setBookAmount(readerModel.getBookAmount());
            }
            if (counter > 0) {
                return readerRepository.save(updatedReaderModel);
            }
        }
        return null;
    }

    public ReaderModel getById(long id) {
        return readerRepository.findById(id).orElse(null);
    }

    public List<ReaderModel> getAll() {
        if (readerRepository.count() > 0) {
            return readerRepository.findAll();
        }
        return null;
    }

    public ReaderModel deleteById(long id) {
        if (readerRepository.existsById(id)) {
            ReaderModel readerModel = readerRepository.findById(id).orElse(null);
            readerRepository.deleteById(id);
            return readerModel;
        }
        return null;
    }

    public ReaderModel getByFirstNameAndLastName(String firstName, String lastName) {
        return readerRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public boolean isExists() {
        return readerRepository.count() > 0;
    }
}
