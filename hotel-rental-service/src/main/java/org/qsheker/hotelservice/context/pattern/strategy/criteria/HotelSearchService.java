package org.qsheker.hotelservice.context.pattern.strategy.criteria;

import org.qsheker.hotelservice.context.db.models.Hotel;
import org.qsheker.hotelservice.context.db.repository.HotelRepository;
import org.qsheker.hotelservice.context.pattern.strategy.PriceSearchStrategy;
import org.qsheker.hotelservice.context.pattern.strategy.RatingSearchStrategy;
import org.qsheker.hotelservice.context.pattern.strategy.SearchStrategy;
import org.qsheker.hotelservice.web.dto.HotelSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelSearchService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private PriceSearchStrategy priceStrategy;

    @Autowired
    private RatingSearchStrategy ratingStrategy;

    public List<Hotel> searchWithStrategy(HotelSearchCriteria criteria, SearchStrategy strategyType) {
        List<Hotel> allHotels = hotelRepository.findByLocationContainingIgnoreCase(
                criteria.getLocation() != null ? criteria.getLocation() : "");
        return strategyType.search(allHotels, criteria);
    }
}
