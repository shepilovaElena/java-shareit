package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> getItemsList(long userId);
    Item getItemById(long id);
    Item addNewItem(Item item);
    Item updateItem(Item item);
    List<Item> searchItems(String text, Long userId);

}
