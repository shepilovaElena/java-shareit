package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemShortDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static Item toItem(ItemCreateDto itemCreateDto) {
        return Item.builder()
                .name(itemCreateDto.getName())
                .owner(itemCreateDto.getOwner())
                .description(itemCreateDto.getDescription())
                .available(itemCreateDto.getAvailable())
                .build();
    }


    public static Item toItem(ItemUpdateDto itemUpdateDto) {
        return Item.builder()
                .name(itemUpdateDto.getName())
                .description(itemUpdateDto.getDescription())
                .available(itemUpdateDto.getAvailable())
                .build();
    }

    public static ItemDto toDto(Item item) {

        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .owner(item.getOwner())
                .description(item.getDescription())
                .request(item.getRequest())
                .available(item.getAvailable())
                .lastBooking(item.getLastBooking())
                .nextBooking(item.getNextBooking())
                .build();
    }


    public static ItemShortDto toShortDto(Item item) {
        return ItemShortDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .build();
    }
}
