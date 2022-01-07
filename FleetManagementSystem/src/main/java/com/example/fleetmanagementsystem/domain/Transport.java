package com.example.fleetmanagementsystem.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "transports")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "originAddress")
    private String originAddress;

    @Column(name = "destinationAddress")
    private String destinationAddress;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "finishDate")
    private Date finishDate;

    @Enumerated(EnumType.STRING)
    private TransportStatusEnum status;

    @JoinColumn(name = "driverId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "carId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;

    @OneToMany(mappedBy = "transport", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<TransportAction> transportActions;

    @OneToOne(mappedBy = "transport")
    private Route route;
}
