package com.example.fleetmanagementsystem.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transportActions")
public class TransportAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TransportActionTypeEnum type;

    @Column(name = "numberOfKm")
    private Integer numberOfKm;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @Column
    private Double currentFuel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transportId")
    private Transport transport;
}
