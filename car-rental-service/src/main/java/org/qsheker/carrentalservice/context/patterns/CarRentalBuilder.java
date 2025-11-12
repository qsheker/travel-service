package org.qsheker.carrentalservice.context.patterns;

import org.qsheker.carrentalservice.context.db.models.CarRental;
import org.qsheker.carrentalservice.context.db.models.RentalStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarRentalBuilder {
    private Long carId;
    private Long userId;
    private LocalDate pickupDate;
    private LocalDate returnDate;
    private String pickupLocation;
    private List<String> extras = new ArrayList<>();
    private Double totalPrice = 0.0;

    public CarRentalBuilder setCar(Long carId) {
        this.carId = carId;
        return this;
    }

    public CarRentalBuilder setUser(Long userId) {
        this.userId = userId;
        return this;
    }

    public CarRentalBuilder setDates(LocalDate pickup, LocalDate returnDate) {
        this.pickupDate = pickup;
        this.returnDate = returnDate;
        return this;
    }

    public CarRentalBuilder addExtra(String extra, Double price) {
        this.extras.add(extra);
        this.totalPrice += price;
        return this;
    }

    public CarRental build() {
        CarRental rental = new CarRental();
        rental.setCarId(this.carId);
        rental.setUserId(this.userId);
        rental.setPickupDate(this.pickupDate);
        rental.setPickupLocation(this.pickupLocation);
        rental.setReturnDate(this.returnDate);
        rental.setPickupLocation(this.pickupLocation);
        rental.setTotalPrice(this.totalPrice);
        rental.setStatus(RentalStatus.CONFIRMED);
        return rental;
    }
}