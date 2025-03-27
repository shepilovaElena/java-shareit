package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> addBooking(@Valid
                                                 @RequestBody
                                                 BookingCreateDto booking,
                                                 @RequestHeader("X-Sharer-User-Id")
                                                 @NotNull
                                                 Long userId) {
        return ResponseEntity.ok(bookingService.createBooking(booking, userId));
    }

    @PatchMapping("{bookingId}")
    public ResponseEntity<BookingDto> bookingConfirmation(@PathVariable
                                                          @NotNull
                                                          Long bookingId,
                                                          @RequestHeader("X-Sharer-User-Id")
                                                          @NotNull
                                                          Long userId,
                                                          @RequestParam
                                                          @NotNull
                                                          Boolean approved) {
        return ResponseEntity.ok(bookingService.bookingConfirmation(bookingId, userId, approved));
    }

    @GetMapping("{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable
                                                     @NotNull
                                                     Long bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllUsersBookings(@RequestParam(defaultValue = "ALL")
                                                                String state,
                                                                @RequestHeader("X-Sharer-User-Id")
                                                                @NotNull
                                                                Long userId) {
        return ResponseEntity.ok(bookingService.getAllUsersBookings(state, userId));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingDto>> getAllBookingsForAllUsersItems(@RequestParam(defaultValue = "ALL")
                                                                           String state,
                                                                           @RequestHeader("X-Sharer-User-Id")
                                                                           @NotNull
                                                                           Long userId) {
        return ResponseEntity.ok(bookingService.getAllBookingsForAllUsersItems(state, userId));
    }
}
