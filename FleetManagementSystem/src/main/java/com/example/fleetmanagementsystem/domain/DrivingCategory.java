package com.example.fleetmanagementsystem.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "drivingCategories")
public class DrivingCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expirationDate")
    private Date expirationDate;

    @Column(name = "issueDate")
    private Date issueDate;

    @JoinColumn(name = "driverId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private LicenseCategoryEnum category;
}
