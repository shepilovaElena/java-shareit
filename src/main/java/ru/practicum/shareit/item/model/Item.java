package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.booking.model.Booking;


/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "items")
public class Item {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   long id;
   String name;
   String description;
   @Column(name = "is_available")
   Boolean available;
   @Column(name = "owner_id", nullable = false)
   Long ownerId;
   String request;
   Booking lastBooking;
   Booking nextBooking;
}
