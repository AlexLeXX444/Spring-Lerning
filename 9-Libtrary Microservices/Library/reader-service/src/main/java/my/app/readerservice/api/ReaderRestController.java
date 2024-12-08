package my.app.readerservice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import my.app.readerservice.model.ReaderModel;
import my.app.readerservice.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reader")
@RequiredArgsConstructor
public class ReaderRestController {

    private final ReaderService readerService;

    @Operation(
            summary = "Создание нового читателя.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно добавили читателя", content = @Content(schema = @Schema(implementation = ReaderModel.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<ReaderModel> createReader(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam int bookAmount
    ) {
        ReaderModel newReader = new ReaderModel(firstName, lastName, bookAmount);

        if (readerService.save(newReader) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(readerService.getByFirstNameAndLastName(firstName, lastName));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @Operation(
            summary = "Изменение имени читателя.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно изменили имя читателя", content = @Content(schema = @Schema(implementation = ReaderModel.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Читатель не найден", content = @Content)
            }
    )
    @PostMapping("/{id}/first-name")
    public ResponseEntity<ReaderModel> updateReaderFirstName(
            @PathVariable long id,
            @RequestParam String firstName
    ) {
        if (readerService.getById(id) != null) {
            ReaderModel reader = new ReaderModel(readerService.getById(id));
            reader.setFirstName(firstName);
            if (readerService.update(reader) != null) {
                return ResponseEntity.status(HttpStatus.OK).body(reader);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Operation(
            summary = "Изменение фамилии читателя.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно изменили фамилию читателя", content = @Content(schema = @Schema(implementation = ReaderModel.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Читатель не найден", content = @Content)
            }
    )
    @PostMapping("/{id}/last-name")
    public ResponseEntity<ReaderModel> updateReaderLastName(
            @PathVariable long id,
            @RequestParam String lastName
    ) {
        if (readerService.getById(id) != null) {
            ReaderModel reader = new ReaderModel(readerService.getById(id));
            reader.setLastName(lastName);
            if (readerService.update(reader) != null) {
                return ResponseEntity.status(HttpStatus.OK).body(reader);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Operation(
            summary = "Изменение максимальное количество книг на руках читателя.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно изменили максимальное количество книг читателя", content = @Content(schema = @Schema(implementation = ReaderModel.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Читатель не найден", content = @Content)
            }
    )
    @PostMapping("/{id}/book-amount")
    public ResponseEntity<ReaderModel> updateReaderBookAmount(
            @PathVariable long id,
            @RequestParam int bookAmount
    ) {
        if (readerService.getById(id) != null) {
            ReaderModel reader = new ReaderModel(readerService.getById(id));
            reader.setBookAmount(bookAmount);
            if (readerService.update(reader) != null) {
                return ResponseEntity.status(HttpStatus.OK).body(reader);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Operation(
            summary = "Получение читателя по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно получили читателя", content = @Content(schema = @Schema(implementation = ReaderModel.class))),
                    @ApiResponse(responseCode = "404", description = "Читатель не найден", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ReaderModel> getById(@PathVariable long id) {
        ReaderModel reader = readerService.getById(id);
        if (reader != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reader);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Operation(
            summary = "Получение списка всех читателей.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно получили список читателей", content = @Content(schema = @Schema(implementation = ReaderModel.class))),
                    @ApiResponse(responseCode = "404", description = "Читатели не найдены", content = @Content)
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<ReaderModel>> getAll() {
        if (readerService.isExists()) {
            return ResponseEntity.status(HttpStatus.OK).body(readerService.getAll());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @Operation(
            summary = "Удаление читателя по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно удалили читателя", content = @Content(schema = @Schema(implementation = ReaderModel.class))),
                    @ApiResponse(responseCode = "404", description = "Читатель не найден", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ReaderModel> deleteById(@PathVariable long id) {
        ReaderModel reader = readerService.getById(id);
        if (readerService.deleteById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reader);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
