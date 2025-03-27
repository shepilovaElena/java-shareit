package ru.practicum.shareit.item;

import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public List<ItemDto> getItemsList(Long userId) {
        checkUserAndGetUserById(userId);
        return itemRepository.findAllByOwnerId(userId).stream()
                .map(itemShort -> ItemDto.builder()
                                         .name(itemShort.getName())
                                         .description(itemShort.getDescription()).build())
                                         .toList();
    }

    @Transactional
    public ItemDto getItemById(long id, long userId) {
        checkUserAndGetUserById(userId);
        List<CommentShortDto> itemComments = commentRepository.findAllByItemId(id).stream()
                .map(CommentMapper::toShortDto)
                .toList();
        Item item = checkAndGetItemById(id);
        ItemDto itemDto = ItemMapper.toDto(item);
        itemDto.setComments(itemComments);
        return itemDto;
    }

    public ItemDto addNewItem(ItemCreateDto itemCreateDto, long userId) {
        User user = checkUserAndGetUserById(userId);
        itemCreateDto.setOwner(user);
        Item item = ItemMapper.toItem(itemCreateDto);
        return ItemMapper.toDto(itemRepository.save(item));
    }

    public ItemDto updateItem(ItemUpdateDto itemUpdateDto, long itemId, long userId) {
        User user = checkUserAndGetUserById(userId);
        Item updatedItem = checkAndGetItemById(itemId);
        checkIsOwner(userId, itemId);

        Item updateItem = ItemMapper.toItem(itemUpdateDto);
        updateItem.setOwner(user);
        updateItem.setId(itemId);

        if (itemUpdateDto.getAvailable() == null) {
            updateItem.setAvailable(updatedItem.getAvailable());
        }
        if (itemUpdateDto.getDescription() == null) {
            updateItem.setDescription(updatedItem.getDescription());
        }
        if (itemUpdateDto.getName() == null) {
            updateItem.setName(updatedItem.getName());
        }
        return ItemMapper.toDto(itemRepository.save(updateItem));
    }

    public List<ItemDto> searchItems(String text, long userId) {
        checkUserAndGetUserById(userId);
        if (text.isBlank()) {
            return List.of();
        }
        return itemRepository.findAllByOwnerIdAndText(userId, text).stream()
                .map(ItemMapper::toDto)
                .toList();
    }

    public CommentDto addNewComment(long itemId, long userId, CommentCreateDto commentCreateDto) {
        Item item = checkAndGetItemById(itemId);
        User user = checkAndGetUserById(userId);
        checkIsBooker(itemId, userId);
        Comment comment = CommentMapper.toComment(commentCreateDto);
        comment.setItem(item);
        comment.setAuthor(user);
        itemRepository.save(item);

        return CommentMapper.toDto(commentRepository.save(comment));
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

    private User checkAndGetUserById(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("");
        }
        return userOptional.get();
    }

    private void checkIsBooker(long itemId, long userId) {
        Optional<Booking> bookingOptional = bookingRepository.findByItemIdAndBookerId(itemId, userId);
        if (bookingOptional.isEmpty()) {
            throw new RuntimeException("User with id " + userId + " is not booker item with id " + itemId + ".");
        }
    }
}
