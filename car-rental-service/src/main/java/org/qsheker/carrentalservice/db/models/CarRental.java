package org.qsheker.carrentalservice.db.models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "car_rentals")
public class CarRental {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(nullable = false)
    private Date pickupDate;

    @Column(nullable = false)
    private Date returnDate;

    @Column(nullable = false)
    private String pickupLocation;

    private String returnLocation;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    private Double totalPrice;

    @CreationTimestamp
    private Date createdAt;
}
