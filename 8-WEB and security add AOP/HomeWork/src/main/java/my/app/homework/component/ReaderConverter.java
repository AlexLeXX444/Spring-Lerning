package my.app.homework.component;

import my.app.homework.dto.ReaderDto;
import my.app.homework.model.Reader;
import org.springframework.stereotype.Component;

@Component
public class ReaderConverter {

    public Reader toEntity(ReaderDto readerDto) {
        Reader reader = new Reader();
        reader.setFirstName(readerDto.getFirstName());
        reader.setLastName(readerDto.getLastName());
        reader.setBookAmount(readerDto.getBookAmount());
        return reader;
    }

    public ReaderDto toDto(Reader reader) {
        ReaderDto readerDto = new ReaderDto();
        readerDto.setFirstName(reader.getFirstName());
        readerDto.setLastName(reader.getLastName());
        readerDto.setBookAmount(reader.getBookAmount());
        return readerDto;
    }
}
