package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.user.model.User;


/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemCreateDto {
    Long id;
    String name;
    String description;
    Boolean available;
    User owner;
    Long request;
}
