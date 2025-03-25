package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

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
        checkUserAndGetUserById(userId);
        return itemRepository.findAllByOwnerId(userId).stream()
                .map(itemShort -> ItemDto.builder()
                                         .name(itemShort.getName())
                                         .description(itemShort.getDescription()).build())
                .toList();
    }

    public ItemDto getItemById(long id, long userId) {
        checkUserAndGetUserById(userId);
        return ItemMapper.toDto(checkAndGetItemById(id));
    }

    public ItemDto addNewItem(ItemCreateDto itemCreateDto, long userId) {
        User user = checkUserAndGetUserById(userId);
        itemCreateDto.setOwner(user);
        Item item = ItemMapper.toItem(itemCreateDto);
        return ItemMapper.toDto(itemRepository.save(item));
    }

    public ItemDto updateItem(ItemUpdateDto itemUpdateDto, long itemId, long userId) {
        User user = checkUserAndGetUserById(userId);
        checkAndGetItemById(itemId);
        checkIsOwner(userId, itemId);

        Item updateItem = ItemMapper.toItem(itemUpdateDto);
        updateItem.setOwner(user);
        updateItem.setId(itemId);
        return ItemMapper.toDto(itemRepository.save(updateItem));
    }

    public List<ItemDto> searchItems(String text, long userId) {
        checkUserAndGetUserById(userId);
        if (text.isBlank()) {
            return List.of();
        }
        return itemRepository.findAllByOwnerIdNameDescriptionContainingIgnoreCase(userId, text).stream()
                .map(ItemMapper::toDto)
                .toList();
    }

    private User checkUserAndGetUserById(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("Item with id = " + userId + " not found.");
        }
        return userOptional.get();
        }

    private Item checkAndGetItemById(Long itemId) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new NoSuchElementException("Item with id = " + itemId + " not found.");
        }
        return itemOptional.get();
    }

    private void checkIsOwner(long userId, long itemId) {
        if (checkAndGetItemById(itemId).getOwner().getId() != userId) {
            throw new NoSuchElementException("User with id = " + userId + " isn't owner for item with id = " + itemId + ".");
        }
    }
}
