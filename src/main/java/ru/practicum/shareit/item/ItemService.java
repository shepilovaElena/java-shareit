package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public List<ItemDto> getItemsList(Long userId) {
        return itemRepository.getItemsList(userId).stream()
                .map(ItemMapper::toDto)
                .toList();
    }

    public ItemDto getItemById(long id, long userId) {
        checkItemId(id, userId);
        return ItemMapper.toDto(itemRepository.getItemById(id));
    }

    public ItemDto addNewItem(ItemCreateDto itemCreateDto, long userId) {
        checkUserId(userId);

        itemCreateDto.setOwnerId(userId);
        Item item = ItemMapper.toItem(itemCreateDto);
        return ItemMapper.toDto(itemRepository.addNewItem(item));
    }

    public ItemDto updateItem(ItemUpdateDto itemUpdateDto, long itemId, long userId) {
        checkUserId(userId);
        checkItemId(itemId, userId);

        Item updateItem = ItemMapper.toItem(itemUpdateDto);
        updateItem.setOwnerId(userId);
        updateItem.setId(itemId);
        return ItemMapper.toDto(itemRepository.updateItem(updateItem));
    }

    public List<ItemDto> searchItems(String text, long userId) {
        return itemRepository.searchItems(text, userId).stream()
                .map(ItemMapper::toDto)
                .toList();
    }

    private void checkUserId(long userId) {
        Optional<User> userOptional = userRepository.getAllUsersList().stream()
                .filter(user -> user.getId() == userId)
                .findFirst();
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("User with id = " + userId + " not found.");
        }
    }

    private void checkItemId(Long itemId, Long userId) {
        Optional<Item> itemOptional = itemRepository.getItemsList(userId).stream()
                .filter(item -> item.getId() == itemId)
                .findFirst();
        if (itemOptional.isEmpty()) {
            throw new NoSuchElementException("Item with id = " + itemId + " not found.");
        }
    }
}
