package org.qsheker.hotelservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class HotelSearchCriteria {
    private String location;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer guests;
    private Double maxPrice;
    private Double minRating;
}
