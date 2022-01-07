package com.example.fleetmanagementsystem.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "createdDate")
    private Date createdDate;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Collection<User> users;
}
