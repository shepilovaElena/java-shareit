package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getAllItemsFromUserList(@RequestHeader("X-Sharer-User-Id")
                                           @Positive(message = "user id must be positive")
                                           @NotNull(message = "the field cannot be empty")
                                           Long userId) {
        log.info("A request has been received to receive all the user's item, user id {}.", userId);
        return itemService.getItemsList(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemInfo(@PathVariable
                               @Positive(message = "item id must be positive")
                               @NotNull(message = "the field cannot be empty")
                               Long itemId,
                               @RequestHeader("X-Sharer-User-Id")
                               @Positive(message = "user id must be positive")
                               @NotNull(message = "the field cannot be empty")
                               Long userId) {
        log.info("A request has been received to receive item's info about item with id {}.", itemId);
        return itemService.getItemById(itemId, userId);
    }

    @PostMapping
    public ResponseEntity<ItemDto> addNewItem(@Valid
                                              @RequestBody
                                              ItemCreateDto itemCreateDto,
                                              @RequestHeader("X-Sharer-User-Id")
                                              @Positive(message = "user id must be positive")
                                              @NotNull(message = "the field cannot be empty")
                                              Long userId) {
            log.info("Received a request to add new item.");
            return ResponseEntity.status(HttpStatus.CREATED).body(itemService.addNewItem(itemCreateDto,userId));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@Valid
                                              @RequestBody
                                              ItemUpdateDto itemUpdateDto,
                                              @PathVariable
                                              @Positive(message = "item id must be positive")
                                              @NotNull(message = "the field cannot be empty")
                                              Long itemId,
                                              @RequestHeader("X-Sharer-User-Id")
                                              @Positive(message = "user id must be positive")
                                              @NotNull(message = "the field cannot be empty")
                                              Long userId) {
        log.info("Received a request to update an item with id {}.", itemId);
        return ResponseEntity.ok(itemService.updateItem(itemUpdateDto, itemId, userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItems(@RequestParam String text,
                                                     @RequestHeader("X-Sharer-User-Id")
                                                     @Positive(message = "user id must be positive")
                                                     @NotNull(message = "the field cannot be empty")
                                                     Long userId) {
        log.info("A search request has been received.");
        return ResponseEntity.ok(itemService.searchItems(text, userId));
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentDto> addComment(@PathVariable
                                                 @NotNull(message = "the field cannot be empty")
                                                 @Positive(message = "user id must be positive")
                                                 Long itemId,
                                                 @RequestHeader("X-Sharer-User-Id")
                                                 @NotNull
                                                 @Positive(message = "user id must be positive")
                                                 Long userId,
                                                 @Valid
                                                 @RequestBody
                                                 CommentCreateDto comment) {
        log.info("Received a request to post comment from user with id {} on item with id {}.", userId, itemId);
        return ResponseEntity.ok(itemService.addNewComment(itemId, userId, comment));
    }
}
