package my.app.homework.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import my.app.homework.component.TokenHandler;
import my.app.homework.model.Note;
import my.app.homework.model.NoteTag;
import my.app.homework.service.NoteTagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/noteTag")
@RequiredArgsConstructor
public class NoteTagRestController {

    private final NoteTagService noteTagService;
    private final TokenHandler tokenHandler;

    @Operation(
            summary = "Создание нового тега заметки.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful created", content = @Content(schema = @Schema(implementation = NoteTag.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token or bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<NoteTag> createNoteTag(
            @RequestBody NoteTagDto noteTagDto,
            @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (noteTagService.create(new NoteTag(noteTagDto.getDescription(), noteTagDto.getColor())) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(noteTagService.findByDescription(noteTagDto.getDescription()));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @Operation(
            summary = "Редактирование тега заметки по ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful updated", content = @Content(schema = @Schema(implementation = NoteTag.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token or bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PostMapping("/{id}")
    public ResponseEntity<NoteTag> updateNoteTag(
            @PathVariable Long id,
            @RequestBody NoteTagDto noteTagDto,
            @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (noteTagService.readById(id) != null) {
            NoteTag noteTag = noteTagService.readById(id);
            if (noteTag != null) {
                if (noteTagDto.getDescription() != null && noteTagDto.getDescription().isEmpty()) {
                    noteTag.setDescription(noteTagDto.getDescription());
                }
                if (noteTagDto.getColor() != null && noteTagDto.getColor().isEmpty()) {
                    noteTag.setColor(noteTagDto.getColor());
                }

                return ResponseEntity.status(HttpStatus.OK).body(noteTagService.update(noteTag));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получение тега заметки по ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful get", content = @Content(schema = @Schema(implementation = NoteTag.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token or bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<NoteTag> findNoteTagById(
            @PathVariable Long id,
            @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        NoteTag noteTag = noteTagService.readById(id);
        if (noteTag != null) {
            return ResponseEntity.status(HttpStatus.OK).body(noteTag);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получение списка всех тегов.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful get", content = @Content(schema = @Schema(implementation = NoteTag.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token or bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<NoteTag>> findAllNoteTags(
            @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader
    ) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<NoteTag> noteTags = noteTagService.readAll();
        if (!noteTags.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(noteTags);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Удаление тега заметки по ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful deleted", content = @Content(schema = @Schema(implementation = NoteTag.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token or bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<NoteTag> deleteNoteTag(
            @PathVariable Long id,
            @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        NoteTag noteTag = noteTagService.readById(id);
        if (noteTag != null) {
            return ResponseEntity.status(HttpStatus.OK).body(noteTagService.deleteById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private NoteTagDto toDto(NoteTag noteTag) {
        return new NoteTagDto(noteTag.getDescription(), noteTag.getColor());
    }
}

@Data
@AllArgsConstructor
class NoteTagDto {
    private String description;
    private String color;
}
