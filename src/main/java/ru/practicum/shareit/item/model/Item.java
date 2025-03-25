package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.model.User;


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
   @ManyToOne
   @JoinColumn(name = "owner_id")
   User owner;
   String request;
//   Booking lastBooking;
//   Booking nextBooking;
}
