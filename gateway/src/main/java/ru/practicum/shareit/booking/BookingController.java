package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingState;


@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {

    private final BookingClient bookingClient;

    ///  нужно ли возвращать dto'шки?

    @PostMapping
    public ResponseEntity<Object> addBooking(@Valid
                                             @RequestBody
                                             BookingCreateDto booking,
                                             @RequestHeader("X-Sharer-User-Id")
                                             @NotNull
                                             Long userId) {
        return bookingClient.postBooking(userId, booking);
    }

    @PatchMapping("{bookingId}")
    public ResponseEntity<Object> bookingConfirmation(@PathVariable
                                                          @NotNull
                                                          Long bookingId,
                                                          @RequestHeader("X-Sharer-User-Id")
                                                          @NotNull
                                                          Long userId,
                                                          @RequestParam
                                                          @NotNull
                                                          Boolean approved) {
        return bookingClient.confirmation(bookingId, userId, approved);
    }

    @GetMapping("{bookingId}")
    public ResponseEntity<Object> getBookingById(@PathVariable
                                                     @NotNull
                                                     Long bookingId,
                                                     @RequestHeader("X-Sharer-User-Id")
                                                     @NotNull
                                                     Long userId) {
        return bookingClient.getBooking(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsersBookings(@RequestParam(defaultValue = "ALL")
                                                      BookingState state,
                                                      @RequestParam(defaultValue = "10")
                                                      Integer size, ///?
                                                      @RequestParam
                                                      Integer from, ///?
                                                      @RequestHeader("X-Sharer-User-Id")
                                                      @NotNull
                                                      Long userId) {
        return bookingClient.getBookings(userId, state, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllBookingsForAllUsersItems(@RequestParam(defaultValue = "ALL")
                                                                           String state,
                                                                           @RequestHeader("X-Sharer-User-Id")
                                                                           @NotNull
                                                                           Long userId) {
        return bookingClient.getBookingsForAllUsersItems(userId, state);
    }
}
