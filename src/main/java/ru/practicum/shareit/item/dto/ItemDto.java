package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {
    long id;
    String name;
    String description;
    boolean available;
    Long ownerId;
    String request;
}
