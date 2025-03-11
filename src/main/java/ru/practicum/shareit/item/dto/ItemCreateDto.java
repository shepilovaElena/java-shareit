package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemCreateDto {
    Long id;
    @NotNull(message = "the field cannot be empty")
    @NotBlank(message = "the field cannot be blank")
    String name;
    @NotNull(message = "the field cannot be empty")
    @NotBlank(message = "the field cannot be blank")
    @Size(max = 200, message = "the description cannot be more than 200 characters long")
    String description;
    @NotNull(message = "the field cannot be empty")
    Boolean available;
    Long ownerId;
    Long request;
}
