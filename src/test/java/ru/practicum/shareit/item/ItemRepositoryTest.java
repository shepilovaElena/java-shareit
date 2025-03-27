package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testSaveAndFind() {
        //given one item
        Item item = newItem();

        //when
        Item created = itemRepository.save(item);
        assertNotNull(created);

        //then
        List<Item> items = itemRepository.findAll();
        assertNotNull(items);
        assertEquals(1, items.size());
    }

    private Item newItem() {
        return Item.builder()
                .id(new Random().nextInt())
                .name("test_item_" + UUID.randomUUID())
                .available(true)
                .description("test_description_" + UUID.randomUUID())
                .build();
    }

}