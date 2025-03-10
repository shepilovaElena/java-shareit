package ru.practicum.shareit.booking;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class Booking {
    long id;
    LocalDateTime start;
    LocalDateTime end;
    @NotNull
    Item item;
    @NotNull
    User booker;
    @NotNull
    BookingStatus status;
}
