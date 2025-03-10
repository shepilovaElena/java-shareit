package ru.practicum.shareit.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
public class ItemRequest {
    long id;
    @NotNull
    @NotBlank
    @Max(200)
    String description;
    @NotNull
    User requestor;
    LocalDateTime created;
}
