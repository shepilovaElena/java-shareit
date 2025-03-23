package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingDto createBooking(BookingCreateDto createBooking) {
        return BookingMapper.toDto(bookingRepository.save(BookingMapper.toBooking(createBooking)));
    }

    public void bookingConfirmation(Long bookingId, boolean approve) {
        checkBookingId(bookingId);

        if (approve) {
            bookingRepository.bookingApprove(bookingId, BookingStatus.APPROVED);
        } else {
            bookingRepository.bookingApprove(bookingId, BookingStatus.REJECTED);
        }
    }

    public BookingDto getBookingById(long bookingId) {
        Optional<Booking> bookingOptional =  bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            throw new NoSuchElementException("Booking with id = " + bookingId + " not found.");
        }
        return BookingMapper.toDto(bookingOptional.get());
    }
@Asse
    public List<BookingDto> getAllUsersBookings(String state, long userId) {
        switch (checkAndGetState(state)) {
            case ALL:
                return bookingRepository.findAllByUserId(userId).stream()
                        .map(BookingMapper::toDto)
                        .toList();
            case PAST:
                return bookingRepository.findAllByUserIdAndEndingBefore(userId,LocalDateTime.now()).stream()
                        .map(BookingMapper::toDto)
                        .toList();
            case FUTURE:
                return bookingRepository.findAllByUserIdAndEndingAfter(userId, LocalDateTime.now()).stream()
                        .map(BookingMapper::toDto)
                        .toList();
            case CURRENT:
                return bookingRepository.
            case WAITING:
                break;
            case REJECTED:
                break;
            default:
                break;
        }
        return null;
    }

    public List<BookingDto> getAllBookingsForAllUsersItems(String state) {
        return null;
    }

    private void checkBookingId(long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new NoSuchElementException("Booking with id = " + bookingId + " not found.");
        }
    }

    private BookingState checkAndGetState(String state) {
        for (BookingState bookingState : BookingState.values()) {
            if (bookingState.name().equalsIgnoreCase(state)) {
                return BookingState.valueOf(state);
            }
        }
          throw new IllegalArgumentException("This status does not exist.");
    }
}
