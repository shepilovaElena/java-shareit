package ru.practicum.shareit.booking;

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
    public ResponseEntity<BookingDto> addBooking(@RequestBody
                                                 BookingCreateDto booking,
                                                 @RequestHeader("X-Sharer-User-Id")
                                                 Long userId) {
        return ResponseEntity.ok(bookingService.createBooking(booking, userId));
    }

    @PatchMapping("{bookingId}")
    public ResponseEntity<BookingDto> bookingConfirmation(@PathVariable
                                                          Long bookingId,
                                                          @RequestHeader("X-Sharer-User-Id")
                                                          Long userId,
                                                          @RequestParam
                                                          Boolean approved) {
        return ResponseEntity.ok(bookingService.bookingConfirmation(bookingId, userId, approved));
    }

    @GetMapping("{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable
                                                     Long bookingId,
                                                     @RequestHeader("X-Sharer-User-Id")
                                                     Long userId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId, userId));
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllUsersBookings(@RequestParam(defaultValue = "ALL")
                                                                String state,
                                                                @RequestParam(defaultValue = "10")
                                                                Integer size,
                                                                @RequestParam
                                                                Integer from,
                                                                @RequestHeader("X-Sharer-User-Id")
                                                                Long userId) {
        return ResponseEntity.ok(bookingService.getAllUsersBookings(state, userId, size, from));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingDto>> getAllBookingsForAllUsersItems(@RequestParam(defaultValue = "ALL")
                                                                           String state,
                                                                           @RequestHeader("X-Sharer-User-Id")
                                                                           Long userId) {
        return ResponseEntity.ok(bookingService.getAllBookingsForAllUsersItems(state, userId));
    }
}
