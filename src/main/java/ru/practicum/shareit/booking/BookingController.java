package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@Validated
public class BookingController {
    @PostMapping
    public ResponseEntity<BookingDto> addBooking(@Valid
                                                 @RequestBody
                                                 BookingCreateDto booking,
                                                 @RequestHeader("X-Sharer-User-Id")
                                                 @NotNull
                                                 Long userId) {
        return null;
    }

    @PatchMapping("{bookingId}")
    public void bookingConfirmation(@PathVariable
                                    @NotNull
                                    Long bookingId,
                                    @RequestParam
                                    @NotNull
                                    Boolean approve) {
    }

    @GetMapping("{bookingId}")
    public ResponseEntity<Booking> getBookingById(@NotNull
                                                  Long bookingId) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllUsersBookings(@RequestParam(defaultValue = "ALL")
                                                             String state,
                                                             @RequestHeader("X-Sharer-User-Id")
                                                             @NotNull
                                                             Long userId) {
        return null;
    }

    @GetMapping("/owner")
    public ResponseEntity<List<Booking>> getAllBookingsForAllUsersItems(@RequestParam(defaultValue = "ALL")
                                                                        String state,
                                                                        @RequestHeader("X-Sharer-User-Id")
                                                                        @NotNull
                                                                        Long userId) {
        return null;
    }
}
