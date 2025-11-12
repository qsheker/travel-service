package org.qsheker.hotelservice.context.pattern.strategy;

import org.qsheker.hotelservice.context.db.models.Hotel;
import org.qsheker.hotelservice.web.dto.HotelSearchCriteria;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceSearchStrategy implements SearchStrategy {

    @Override
    public List<Hotel> search(List<Hotel> hotels, HotelSearchCriteria criteria) {
        return hotels.stream()
                .filter(hotel -> criteria.getMaxPrice() == null ||
                        hotel.getPricePerNight() <= criteria.getMaxPrice())
                .filter(hotel -> criteria.getLocation() == null ||
                        hotel.getLocation().toLowerCase().contains(criteria.getLocation().toLowerCase()))
                .sorted(Comparator.comparing(Hotel::getPricePerNight))
                .collect(Collectors.toList());
    }
}