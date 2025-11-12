package org.qsheker.hotelservice.api;

import org.qsheker.hotelservice.context.db.models.Hotel;
import org.qsheker.hotelservice.context.db.models.HotelBooking;
import org.qsheker.hotelservice.context.service.impl.HotelService;
import org.qsheker.hotelservice.web.dto.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(
            @RequestParam String location,
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut,
            @RequestParam Integer guests) {

        List<Hotel> hotels = hotelService.searchHotels(location, checkIn, checkOut, guests);
        return ResponseEntity.ok(hotels);
    }

    @PostMapping("/book")
    public ResponseEntity<HotelBooking> bookHotel(@RequestBody BookingRequest request) {
        HotelBooking booking = hotelService.bookHotel(
                request.getHotelId(),
                request.getUserId(),
                request.getCheckIn(),
                request.getCheckOut(),
                request.getGuests()
        );
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/users/{userId}/bookings")
    public ResponseEntity<List<HotelBooking>> getUserBookings(@PathVariable Long userId) {
        List<HotelBooking> bookings = hotelService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<HotelBooking> cancelBooking(@PathVariable Long bookingId) {
        HotelBooking booking = hotelService.cancelBooking(bookingId);
        return ResponseEntity.ok(booking);
    }
}