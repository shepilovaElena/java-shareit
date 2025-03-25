package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingDto createBooking(BookingCreateDto createBooking, long userId) {
        createBooking.setBooker(checkAndGetUserById(userId));
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

    public List<BookingDto> getAllUsersBookings(String state, long userId) {
        checkAndGetUserById(userId);
        switch (checkAndGetState(state)) {
            case ALL:
                return bookingRepository.findAllByBookerId(userId).stream()
                        .map(BookingMapper::toDto)
                        .toList();
            case PAST:
                return bookingRepository.findAllByBookerIdAndEndingBefore(userId,LocalDateTime.now()).stream()
                        .map(BookingMapper::toDto)
                        .toList();
            case FUTURE:
                return bookingRepository.findAllByBookerIdAndEndingAfter(userId, LocalDateTime.now()).stream()
                        .map(BookingMapper::toDto)
                        .toList();
            case CURRENT:
                return bookingRepository.findAllByBookerIdCurrentBooking(userId, LocalDateTime.now()).stream()
                        .map(BookingMapper::toDto)
                        .toList();
            case WAITING:
                return bookingRepository.findAllByBookerIdAndStatus(userId, BookingStatus.WAITING).stream()
                        .map(BookingMapper::toDto)
                        .toList();
            case REJECTED:
                return bookingRepository.findAllByBookerIdAndStatus(userId, BookingStatus.REJECTED).stream()
                        .map(BookingMapper::toDto)
                        .toList();
            default:
                throw new IllegalArgumentException("This state does not exist.");
        }
    }

//    public List<BookingDto> getAllBookingsForAllUsersItems(String state, long userId) {
//        switch (checkAndGetState(state)) {
//            case ALL:
//                return bookingRepository.findAllByBookerId(userId).stream()
//                        .map(BookingMapper::toDto)
//                        .toList();
//            case PAST:
//                return bookingRepository.findAllByUserIdAndEndingBefore(userId,LocalDateTime.now()).stream()
//                        .map(BookingMapper::toDto)
//                        .toList();
//            case FUTURE:
//                return bookingRepository.findAllByUserIdAndEndingAfter(userId, LocalDateTime.now()).stream()
//                        .map(BookingMapper::toDto)
//                        .toList();
//            case CURRENT:
//                return bookingRepository.findAllByUserIdCurrentBooking(userId, LocalDateTime.now()).stream()
//                        .map(BookingMapper::toDto)
//                        .toList();
//            case WAITING:
//                return bookingRepository.findAllByUserIdAndStatus(userId, BookingStatus.WAITING).stream()
//                        .map(BookingMapper::toDto)
//                        .toList();
//            case REJECTED:
//                return bookingRepository.findAllByUserIdAndStatus(userId, BookingStatus.REJECTED).stream()
//                        .map(BookingMapper::toDto)
//                        .toList();
//            default:
//                throw new IllegalArgumentException("This state does not exist.");
//        }
//    }

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
          throw new IllegalArgumentException("This state does not exist.");
    }

    private User checkAndGetUserById(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("User with id = " + userId + " not found.");
        }
        return userOptional.get();
    }
}
