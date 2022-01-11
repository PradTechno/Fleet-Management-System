package com.example.fleetmanagementsystem.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String brand;

    @Column
    private String model;

    @Column(name = "modelYear")
    private Integer modelYear;

    @Column(name = "tankCapacity")
    private Integer tankCapacity;

    @Column(name = "licenseNeeded")
    @Enumerated(EnumType.STRING)
    private LicenseCategoryEnum licenseNeeded;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @Column(name = "createdBy")
    private String createdBy;

    @JoinColumn(name = "companyId")
    @ManyToOne(fetch=FetchType.LAZY)
    private Company company;

    @OneToMany(mappedBy="car", fetch = FetchType.LAZY)
    private Collection<CarMaintenance> carMaintenances;

    @OneToMany(mappedBy="car", fetch = FetchType.LAZY)
    private Set<Transport> transports;

}
