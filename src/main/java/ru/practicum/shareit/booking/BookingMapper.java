package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

public class BookingMapper {

    public static Booking toBooking(BookingCreateDto bookingCreateDto) {
        return Booking.builder()
                .start(bookingCreateDto.getStart())
                .ending(bookingCreateDto.getEnd())
                .item(bookingCreateDto.getItem())
                .booker(bookingCreateDto.getBooker())
                .status(bookingCreateDto.getStatus())
                .build();
    }


    public static BookingDto toDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnding())
                .item(booking.getItem())
                .status(booking.getStatus())
                .booker(booking.getBooker())
                .build();
    }
}

