package org.qsheker.hotelservice.context.service.impl;

import jakarta.transaction.Transactional;
import org.qsheker.hotelservice.context.db.models.BookingStatus;
import org.qsheker.hotelservice.context.db.models.Hotel;
import org.qsheker.hotelservice.context.db.models.HotelBooking;
import org.qsheker.hotelservice.context.db.repository.HotelBookingRepository;
import org.qsheker.hotelservice.context.db.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelBookingRepository bookingRepository;

    public List<Hotel> searchHotels(String location, LocalDate checkIn, LocalDate checkOut, Integer guests) {
        return hotelRepository.findByLocationContainingIgnoreCase(location);
    }

    public HotelBooking bookHotel(Long hotelId, Long userId, LocalDate checkIn, LocalDate checkOut, Integer guests) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        Double totalPrice = hotel.getPricePerNight() * days;

        HotelBooking booking = new HotelBooking();
        booking.setHotel(hotel);
        booking.setUserId(userId);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setGuests(guests);
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(booking);
    }

    public List<HotelBooking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public HotelBooking cancelBooking(Long bookingId) {
        HotelBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }
}