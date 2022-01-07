package com.example.fleetmanagementsystem.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "carMaintenances")
public class CarMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numberOfKm")
    private Integer numberOfKm;

    @Column(name = "setDate")
    private LocalDate setDate;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @Column(name = "createdBy")
    private String createdBy;

    @Column
    private Boolean checked;

    @JoinColumn(name = "carId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;
}
