package my.app.homework.controller;

import lombok.RequiredArgsConstructor;
import my.app.homework.model.Reader;
import my.app.homework.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @PostMapping
    public ResponseEntity<Reader> addReader(@RequestBody Reader reader) {
        if (readerService.getReaderByName(reader.getName()) == null) {
            return ResponseEntity.ok(readerService.save(reader));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(reader);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reader>> getAllReaders() {
        if (readerService.getAllReaders().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(readerService.getAllReaders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable long id) {
        if (readerService.getReaderById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(readerService.getReaderById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reader> deleteReaderById(@PathVariable long id) {
        if (readerService.getReaderById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(readerService.deleteReader(id));
    }
}
