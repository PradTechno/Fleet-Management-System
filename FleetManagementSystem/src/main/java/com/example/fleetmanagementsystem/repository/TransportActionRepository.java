package com.example.fleetmanagementsystem.repository;

import com.example.fleetmanagementsystem.domain.Transport;
import com.example.fleetmanagementsystem.domain.TransportAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TransportActionRepository extends JpaRepository<TransportAction, Long> {
    TransportAction save(TransportAction transportAction);

    Collection<TransportAction> findAllByTransport(Transport transport);

}