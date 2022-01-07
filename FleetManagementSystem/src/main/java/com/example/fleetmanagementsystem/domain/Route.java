package com.example.fleetmanagementsystem.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column(name = "totalDistance")
    private Integer totalDistance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transportId")
    private Transport transport;
}
