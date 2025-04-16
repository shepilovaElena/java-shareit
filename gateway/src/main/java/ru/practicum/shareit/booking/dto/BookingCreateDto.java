package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingCreateDto {
    @NotNull(message = "the field cannot be empty")
    @Future(message = "the start of booking should be in the future")
    LocalDateTime start;
    @NotNull(message = "the field cannot be empty")
    @Future(message = "the end of booking should be in the future")
    LocalDateTime end;
    @NotNull(message = "the field cannot be empty")
    Long itemId;
    Long bookerId;
    BookingStatus status;

    @AssertTrue(message = "the start of the booking must be earlier than the end")
    boolean isStartBeforeEnd() {
        return start.isBefore(end);
    }
}
