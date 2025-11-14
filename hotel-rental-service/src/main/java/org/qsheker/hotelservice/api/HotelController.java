package org.qsheker.hotelservice.api;

import org.qsheker.hotelservice.context.db.models.Hotel;
import org.qsheker.hotelservice.context.db.models.HotelBooking;
import org.qsheker.hotelservice.context.pattern.factory.StrategyFactory;
import org.qsheker.hotelservice.context.pattern.strategy.SearchStrategy;
import org.qsheker.hotelservice.context.pattern.strategy.StrategyType;
import org.qsheker.hotelservice.context.pattern.strategy.criteria.HotelSearchService;
import org.qsheker.hotelservice.context.service.impl.HotelService;
import org.qsheker.hotelservice.web.dto.BookingRequest;
import org.qsheker.hotelservice.web.dto.HotelSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelSearchService hotelSearchService;
    @Autowired
    private StrategyFactory strategyFactory;


    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam(required = false) Integer guests,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minRating,
            @RequestParam(defaultValue = "PRICE") StrategyType strategy) {

        SearchStrategy searchStrategy = strategyFactory.of(strategy);
        HotelSearchCriteria criteria = new HotelSearchCriteria(location, checkIn, checkOut, guests, maxPrice, minRating);
        List<Hotel> hotels = hotelSearchService.searchWithStrategy(criteria, searchStrategy);

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