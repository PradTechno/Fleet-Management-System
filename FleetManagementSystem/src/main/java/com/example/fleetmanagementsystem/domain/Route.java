package com.example.fleetmanagementsystem.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "routes")
public class Route {
    @Id
    private Long id;

    @Column(name = "totalDuration")
    private Long totalDuration;

    @Column(name = "totalDistance")
    private Long totalDistance;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Transport transport;

    public Route(Long distance, Long duration) {
        super();

        this.totalDistance = distance;
        this.totalDuration = duration;
    }
}
