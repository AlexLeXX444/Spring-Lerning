package my.app.litehomework.api;

import lombok.RequiredArgsConstructor;
import my.app.litehomework.model.Reader;
import my.app.litehomework.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reader")
@RequiredArgsConstructor
public class ReaderRestController {

    private final ReaderService readerService;

    @PostMapping
    public ResponseEntity<Reader> addNewReader(@RequestBody Reader reader) {
        if (readerService.addReader(reader) != null) {
            return ResponseEntity.ok().body(readerService.getByName(reader.getName()));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable long id) {
        if (readerService.getById(id) != null) {
            return ResponseEntity.ok().body(readerService.getById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reader>> getAllReaders() {
        if (!readerService.getAll().isEmpty()) {
            return ResponseEntity.ok().body(readerService.getAll());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reader> deleteReader(@PathVariable long id) {
        Reader reader = readerService.getById(id);
        if (reader != null) {
            return ResponseEntity.ok().body(readerService.deleteById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
