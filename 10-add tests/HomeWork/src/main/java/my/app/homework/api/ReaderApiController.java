package my.app.homework.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import my.app.homework.component.ReaderConverter;
import my.app.homework.dto.ReaderDto;
import my.app.homework.model.Reader;
import my.app.homework.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reader")
@RequiredArgsConstructor
public class ReaderApiController {

    private final ReaderService readerService;
    private final ReaderConverter readerConverter;

    @Operation(
            summary = "Добавление нового читателя.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно добавлен", content = @Content(schema = @Schema(implementation = Reader.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<Reader> addReader(ReaderDto readerDto) {
        Reader reader = readerConverter.toEntity(readerDto);
        if (readerService.save(reader) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reader);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Изменение имени читателя.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно изменено", content = @Content(schema = @Schema(implementation = Reader.class))),
                    @ApiResponse(responseCode = "404", description = "Читатель не найден", content = @Content)
            }
    )
    @PostMapping("/{id}/first-name")
    public ResponseEntity<Reader> editFirstName(@PathVariable("id") Long id, @RequestBody String firstName) {
        Reader reader = readerService.getById(id);
        if (reader != null && firstName != null) {
            reader.setFirstName(firstName);
            readerConverter.toDto(reader);
            return ResponseEntity.status(HttpStatus.OK).body(reader);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Изменение фамилии читателя.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно изменено", content = @Content(schema = @Schema(implementation = Reader.class))),
                    @ApiResponse(responseCode = "404", description = "Читатель не найден", content = @Content)
            }
    )
    @PostMapping("/{id}/last-name")
    public ResponseEntity<Reader> editLastName(@PathVariable("id") Long id, @RequestBody String lastName) {
        Reader reader = readerService.getById(id);
        if (reader != null && lastName != null) {
            reader.setLastName(lastName);
            readerService.save(reader);
            return ResponseEntity.status(HttpStatus.OK).body(reader);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Изменение разрешенного количества книг читателя.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно изменено", content = @Content(schema = @Schema(implementation = Reader.class))),
                    @ApiResponse(responseCode = "404", description = "Читатель не найден", content = @Content)
            }
    )
    @PostMapping("/{id}/books-amount")
    public ResponseEntity<Reader> editBooksAmount(@PathVariable("id") Long id, @RequestBody int booksAmount) {
        Reader reader = readerService.getById(id);
        if (reader != null && booksAmount >= 0) {
            reader.setBookAmount(booksAmount);
            return ResponseEntity.status(HttpStatus.OK).body(readerService.save(reader));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получение читателя по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получено", content = @Content(schema = @Schema(implementation = Reader.class))),
                    @ApiResponse(responseCode = "404", description = "Читатель не найден", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable("id") Long id) {
        Reader reader = readerService.getById(id);
        if (reader != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reader);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получение списка всех читателей.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получены", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Reader.class)))),
                    @ApiResponse(responseCode = "404", description = "Читатели не найдены", content = @Content)
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<Reader>> getAllReaders() {
        List<Reader> readers = readerService.getAll();
        if (readers != null) {
            return ResponseEntity.status(HttpStatus.OK).body(readers);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Удаление читателя по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно удалено", content = @Content(schema = @Schema(implementation = Reader.class))),
                    @ApiResponse(responseCode = "404", description = "Читатель не найден", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Reader> deleteReader(@PathVariable("id") Long id) {
        Reader reader = readerService.getById(id);
        if (reader != null) {
            return ResponseEntity.status(HttpStatus.OK).body(readerService.deleteById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
