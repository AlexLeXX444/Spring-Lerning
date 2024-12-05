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
import my.app.homework.service.NoteService;
import my.app.homework.service.NoteTagService;
import my.app.homework.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteRestController {

    private final NoteService noteService;
    private final UserService userService;
    private final NoteTagService noteTagService;
    private final TokenHandler tokenHandler;

    @Operation(
            summary = "Создание новой заметки.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful created", content = @Content(schema = @Schema(implementation = Note.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token or bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<Note> createNote(
            @RequestBody NoteDto noteDto,
            @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        System.out.println(authorizationHeader);
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (noteService.create(toEntity(noteDto)) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(noteService.readByTitle(noteDto.getTitle()));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(
            summary = "Привязка тега к заметке.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful updated", content = @Content(schema = @Schema(implementation = Note.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token or bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PostMapping("/{id}/set-tag")
    public ResponseEntity<Note> setNoteTag(
            @PathVariable("id") long id,
            @RequestParam("tagId") long tagId,
            @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Note note = noteService.readById(id);
        NoteTag noteTag = noteTagService.readById(tagId);

        if (note != null && noteTag != null) {
            note.getTags().add(noteTag);
            return ResponseEntity.status(HttpStatus.OK).body(noteService.update(note));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Внесение изменений в уже существующую заметку.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful updated", content = @Content(schema = @Schema(implementation = Note.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PostMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable("id") long id,
            @RequestBody NoteDto noteDto,
            @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Note note = noteService.readById(id);
        if (note != null) {
            note.setTitle(noteDto.getTitle());
            note.setContent(noteDto.getContent());
            note.setUser(userService.readById(noteDto.getUsersId()));
            note.setTags(toEntity(noteDto).getTags());
            return ResponseEntity.status(HttpStatus.OK).body(noteService.update(note));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получить заметку по ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful get", content = @Content(schema = @Schema(implementation = Note.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Note> readNote(
            @PathVariable("id") long id,
            @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Note note = noteService.readById(id);
        if (note != null) {
            return ResponseEntity.status(HttpStatus.OK).body(note);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получить все заметки.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful get", content = @Content(schema = @Schema(implementation = Note.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<Note>> readAll(
            @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Note> notes = noteService.readAll();
        if (notes != null && !notes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(notes);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получить список заметок по определенному тегу.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful get", content = @Content(schema = @Schema(implementation = Note.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @GetMapping("/all/by-tag-id")
    public ResponseEntity<List<Note>> readAllByTag(
            @RequestParam("tagId") long tagId,
            @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        NoteTag noteTag = noteTagService.readById(tagId);
        if (noteTag != null && !noteTag.getNotes().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(noteTag.getNotes());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Удаление заметки по ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful delete", content = @Content(schema = @Schema(implementation = Note.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @PathVariable("id") long id,
            @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (noteService.deleteById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    private Note toEntity(NoteDto noteDto) {
        List<NoteTag> tags = new ArrayList<>();

        if (noteDto != null) {
            if (!noteDto.getTagsId().isEmpty()) {
                for (Long id : noteDto.getTagsId()) {
                    tags.add(noteTagService.readById(id));
                }
            }

            return new Note(noteDto.getTitle(), noteDto.getContent(), userService.readById(noteDto.getUsersId()), tags);
        }
        return null;
    }
}

@Data
@AllArgsConstructor
class NoteDto {
    private String title;
    private String content;
    private long usersId;
    private List<Long> tagsId;
}
