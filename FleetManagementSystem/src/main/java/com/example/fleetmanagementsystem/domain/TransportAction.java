package com.example.fleetmanagementsystem.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @Column
    private Double currentFuel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transportId")
    private Transport transport;
}
