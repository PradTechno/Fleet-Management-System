package com.example.fleetmanagementsystem.service;

import com.example.fleetmanagementsystem.domain.Transport;
import com.example.fleetmanagementsystem.domain.TransportAction;
import com.example.fleetmanagementsystem.repository.TransportActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TransportActionService {

    @Autowired
    private TransportActionRepository transportActionRepository;

    public Collection<TransportAction> getActionsForTransport(Transport transport) {
        return transportActionRepository.findAllByTransport(transport);
    }

    public TransportAction save(TransportAction transportAction) {
        return transportActionRepository.save(transportAction);
    }

}
