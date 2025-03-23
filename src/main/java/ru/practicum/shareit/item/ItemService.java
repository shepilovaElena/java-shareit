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
        checkUserId(userId);
        return itemRepository.findAllByUserId(userId).stream()
                .map(itemShort -> ItemDto.builder()
                                         .name(itemShort.getName())
                                         .description(itemShort.getDescription()).build())
                .toList();
    }

    public ItemDto getItemById(long id, long userId) {
        checkUserId(userId);
        return ItemMapper.toDto(checkAndGetItemById(id));
    }

    public ItemDto addNewItem(ItemCreateDto itemCreateDto, long userId) {
        checkUserId(userId);
        itemCreateDto.setOwnerId(userId);
        Item item = ItemMapper.toItem(itemCreateDto);
        return ItemMapper.toDto(itemRepository.save(item));
    }

    public ItemDto updateItem(ItemUpdateDto itemUpdateDto, long itemId, long userId) {
        checkUserId(userId);
        checkAndGetItemById(itemId);
        checkIsOwner(userId, itemId);

        Item updateItem = ItemMapper.toItem(itemUpdateDto);
        updateItem.setOwnerId(userId);
        updateItem.setId(itemId);
        return ItemMapper.toDto(itemRepository.save(updateItem));
    }

    public List<ItemDto> searchItems(String text, long userId) {
        checkUserId(userId);
        if (text.isBlank()) {
            return List.of();
        }
        return itemRepository.findAllByUserIdNameAndDescriptionContainingIgnoreCase(userId, text).stream()
                .map(ItemMapper::toDto)
                .toList();
    }

    private void checkUserId(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("Item with id = " + userId + " not found.");
        }
        }

    private Item checkAndGetItemById(Long itemId) {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new NoSuchElementException("Item with id = " + itemId + " not found.");
        }
        return itemOptional.get();
    }

    private void checkIsOwner(long userId, long itemId) {
        if (checkAndGetItemById(itemId).getOwnerId() != userId) {
            throw new NoSuchElementException("User with id = " + userId + " isn't owner for item with id = " + itemId + ".");
        }
    }
}
