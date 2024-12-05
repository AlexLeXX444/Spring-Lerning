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
import my.app.homework.model.User;
import my.app.homework.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final TokenHandler tokenHandler;

    @Operation(
            summary = "Добавление нового пользователя.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful created", content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token or bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto user, @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (userService.create(new User(user.getFirstName(), user.getLastName())) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.getByNames(user.getFirstName(), user.getLastName()));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @Operation(
            summary = "Редактирование пользователя по ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful updated", content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token or bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PostMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody UserDto user, @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User updatedUser = userService.readById(id);
        if (updatedUser != null && user != null) {
                if (user.getFirstName() != null) {
                    updatedUser.setFirstName(user.getFirstName());
                }
                if (user.getLastName() != null) {
                    updatedUser.setLastName(user.getLastName());
                }
                return ResponseEntity.status(HttpStatus.OK).body(userService.update(updatedUser));
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @Operation(
            summary = "Получение пользователя по ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful get", content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token or bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id, @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (userService.readById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.readById(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Получение списка всех пользователей.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful get", content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token or bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (userService.count() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.readAll());
    }

    @Operation(
            summary = "Удаление пользователя по ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful deleted", content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "No authorization token or bad request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Wrong authorization token", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id, @RequestHeader("Authorization") @Parameter(hidden = true) String authorizationHeader) {
        if (!tokenHandler.isValidToken(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User deletedUser = userService.readById(id);
        if (deletedUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.deleteById(deletedUser.getId()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getFirstName(), user.getLastName());
    }
}

@Data
@AllArgsConstructor
class UserDto {
    private String firstName;
    private String lastName;
}
