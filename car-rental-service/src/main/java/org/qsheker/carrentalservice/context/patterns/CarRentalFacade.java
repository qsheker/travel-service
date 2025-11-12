package org.qsheker.carrentalservice.context.patterns;

import org.qsheker.carrentalservice.context.db.models.Car;
import org.qsheker.carrentalservice.context.db.models.CarRental;
import org.qsheker.carrentalservice.context.db.repository.CarRentalRepository;
import org.qsheker.carrentalservice.context.db.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CarRentalFacade {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarRentalRepository rentalRepository;

    public CarRental quickRent(Long carId, Long userId, LocalDate pickupDate,
                               LocalDate returnDate, String location) {

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not available"));

        long days = ChronoUnit.DAYS.between(pickupDate, returnDate);
        Double basePrice = car.getDailyPrice() * days;

        CarRental rental = new CarRentalBuilder()
                .setCar(carId)
                .setUser(userId)
                .setDates(pickupDate, returnDate)
                .addExtra("INSURANCE", 25.0)
                .build();

        return rentalRepository.save(rental);
    }
}