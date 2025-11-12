package org.qsheker.carrentalservice.db.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String category;

    private Integer seats;

    private String transmission;

    @Column(nullable = false)
    private Double dailyRate;

    @Column(nullable = false)
    private String location;

    @ElementCollection
    private List<String> features;

    @CreationTimestamp
    private Date createdAt;
}
