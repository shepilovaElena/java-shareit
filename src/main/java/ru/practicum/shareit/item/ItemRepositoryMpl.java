package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepositoryMpl implements ItemRepository {
    Map<Long, Item> itemMap = new HashMap<>();
    long count = 0;

    @Override
    public List<Item> getItemsList(long userId) {

        return itemMap.values().stream()
                .filter(item -> item.getOwnerId() == userId)
                .toList();
    }

    @Override
    public Item getItemById(long id) {
            return itemMap.get(id);
    }

    @Override
    public Item addNewItem(Item item) {
        count++;
        item.setId(count);
        itemMap.put(count, item);
        return item;
    }

    @Override
    public Item updateItem(Item updateItem) {
        Item item = itemMap.get(updateItem.getId());
            updateItem.setRequest(item.getRequest());
            if (updateItem.getName() == null) {
                updateItem.setName(item.getName());
            }
            if (updateItem.getDescription() == null) {
                updateItem.setDescription(item.getDescription());
            }
            if (updateItem.getAvailable() == null) {
                updateItem.setAvailable(item.getAvailable());
            }
            itemMap.put(item.getId(), updateItem);
            return updateItem;
    }

    @Override
    public List<Item> searchItems(String text, Long userId) {
        return itemMap.values().stream()
                .filter(item -> item.getAvailable())
                .filter(item ->
                        (item.getName().toLowerCase().contains(text.toLowerCase())
                                || item.getDescription().toLowerCase().contains(text.toLowerCase())))
                .toList();
    }
}
