package ru.practicum.shareit.booking;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class BookingRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;






    @Test
    void findCountBookingsForItem() {
        Item item1 = newItem();
        Item item2 = newItem();
        Item item3 = newItem();

        User user1 = newUser();

        Booking testBooking = Booking.builder()
                .item(item1)
                .start(LocalDateTime.of(2025, 4, 3, 12, 3))
                .ending(LocalDateTime.of(2025, 4, 5, 12, 3))
                .booker(user1)
                .status(BookingStatus.APPROVED)
                .build();

        bookingRepository.save(testBooking);


        long i = bookingRepository.findCountBookingsForItem(item1.getId(), LocalDateTime.of(2025, 4, 4, 12, 3),
                LocalDateTime.of(2025, 4, 5, 12, 3));


        Assertions.assertEquals(1, i);
    }

    private Item newItem() {
        return Item.builder()
                .id(new Random().nextInt())
                .name("test_item_" + UUID.randomUUID())
                .available(true)
                .description("test_description_" + UUID.randomUUID())
                .build();
    }

    private User newUser() {
        return User.builder()
                .id(new Random().nextInt())
                .name("test_item_" + UUID.randomUUID())
                .build();
    }
}