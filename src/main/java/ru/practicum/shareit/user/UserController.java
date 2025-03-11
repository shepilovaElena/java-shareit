package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@Slf4j
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsersList() {
        return userService.getAllUsersList();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable
                                               @NotNull(message = "the field cannot be empty")
                                               @Positive(message = "user id must be positive")
                                               Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid
                                              @RequestBody
                                              @NotNull(message = "the field cannot be empty")
                                              UserCreateDto userCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userCreateDto));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable
                                        @NotNull(message = "the field cannot be empty")
                                        @Positive(message = "user id must be positive")
                                        Long userId,
                                        @Valid
                                        @RequestBody
                                        @NotNull(message = "the field cannot be empty")
                                        UserUpdateDto userUpdateDto) {
        return ResponseEntity.status(200).body(userService.updateUser(userId, userUpdateDto));
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable
                               @NotNull(message = "the field cannot be empty")
                               @Positive(message = "user id must be positive")
                               Long userId) {
        userService.deleteUserById(userId);
    }
}
