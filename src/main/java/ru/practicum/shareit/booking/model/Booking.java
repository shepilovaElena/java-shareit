package ru.practicum.shareit.booking.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotNull
    LocalDateTime start;
    @NotNull
    LocalDateTime ending;
    @ManyToOne
    @JoinColumn(name = "item_id")
    @NotNull
    Item item;
    @ManyToOne
    @JoinColumn(name = "booker_id")
    @NotNull
    User booker;
    @Enumerated(EnumType.STRING)
    BookingStatus status;

}
