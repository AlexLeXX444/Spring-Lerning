package my.app.readerservice.component;

import lombok.RequiredArgsConstructor;
import my.app.readerservice.model.ReaderModel;
import my.app.readerservice.service.ReaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FillDataBase implements CommandLineRunner {

    private final ReaderService readerService;


    @Override
    public void run(String... args) throws Exception {
        fillDataBase();
    }

    private void fillDataBase() {
        List<ReaderModel> readers = new ArrayList<>();

        readers.add(new ReaderModel("Иван", "Иванов", 0));
        readers.add(new ReaderModel("Анна", "Петрова", 1));
        readers.add(new ReaderModel("Сергей", "Сидоров", 2));
        readers.add(new ReaderModel("Елена", "Кузнецова", 3));
        readers.add(new ReaderModel("Дмитрий", "Орлов", 4));
        readers.add(new ReaderModel("Ольга", "Николаева", 5));
        readers.add(new ReaderModel("Алексей", "Федоров", 0));
        readers.add(new ReaderModel("Марина", "Смирнова", 1));
        readers.add(new ReaderModel("Владимир", "Попов", 2));
        readers.add(new ReaderModel("Татьяна", "Морозова", 3));

        for (ReaderModel reader : readers) {
            readerService.save(reader);
        }
    }
}
