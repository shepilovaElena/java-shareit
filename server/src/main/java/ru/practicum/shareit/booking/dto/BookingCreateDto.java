package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.BookingStatus;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingCreateDto {
    LocalDateTime start;
    LocalDateTime end;
    Long itemId;
    Long bookerId;
    BookingStatus status;

//    @AssertTrue(message = "the start of the booking must be earlier than the end")
//    boolean isStartBeforeEnd() {
//        return start.isBefore(end);
//    }
}
