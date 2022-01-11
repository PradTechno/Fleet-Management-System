package com.example.fleetmanagementsystem.service;

import com.example.fleetmanagementsystem.domain.*;
import com.example.fleetmanagementsystem.exception.CarNotFoundException;
import com.example.fleetmanagementsystem.exception.TransportNotFoundException;
import com.example.fleetmanagementsystem.repository.TransportRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class TransportService {
    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private RouteApiService routeApiService;

    @Autowired
    private TransportActionService transportActionService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CompanyService companyService;

    private Transport save(Transport transport) {
        return transportRepository.save(transport);
    }

    private void delete(Transport transport) {
        transportRepository.delete(transport);
    }

    private Transport getTransportWithCar(Long id) {
        return transportRepository.findOneWithCar(id).orElseThrow(() -> new TransportNotFoundException("Transport not found."));
    }

//    private Transport getTransport(Long id) {
//        return transportRepository.findById(id).orElseThrow(() -> new TransportNotFoundException("Transport not found."));
//    }

    private Transport getTransportWithCompany(Long id) {
        var transport = getTransportWithCar(id);
        var car = carService.getCarWithCompany(transport.getCar().getId());
        transport.setCar(car);
        return transport;
    }

    private Transport getTransportWithCarAndDriver(Long id) {
        return transportRepository.findOneWithCarAndDriver(id).orElseThrow(() -> new TransportNotFoundException("Transport not found."));
    }

    private Transport getTransportWithUserAndActions(Long id) {
        return transportRepository.findOneWithDriverAndActions(id).orElseThrow(() -> new TransportNotFoundException("Transport not found."));
    }

    private Car getCarForTransportIfBelongsToCompany(Transport transport, Long companyId) {
        var car = carService.getCarWithCompany(transport.getCar().getId());
        if (!Objects.equals(car.getCompany().getId(), companyId)) {
            throw new CarNotFoundException("This car doesn't belong to your fleet.");
        }
        return car;
    }

    private User getDriverForTransportIfBelongsToCompany(Transport transport, Long companyId) {
        var driver = userService.findUser(transport.getUser().getUsername());
        if (!Objects.equals(driver.getCompany().getId(), companyId)) {
            throw new UsernameNotFoundException("This user doesn't belong to your fleet.");
        }
        return driver;
    }

    public List<Transport> findAllByCar(Car car) {
        return transportRepository.findAllByCar(car);
    }

    @Transactional
    public Transport save(Transport transport, Long companyId) {
        getCarForTransportIfBelongsToCompany(transport, companyId);
        var driver = getDriverForTransportIfBelongsToCompany(transport, companyId);

        transport.setStatus(TransportStatusEnum.NotStarted);

        try {
            var route = routeApiService.calculateRoute(transport.getOriginAddress(), transport.getDestinationAddress());
            route.setTransport(transport);
            transport.setRoute(route);
        } catch (JsonProcessingException ignored) {
        }

        transport = save(transport);
        emailService.sendTransportHasBeenAssignedEmailToDriver(driver.getUsername(), transport);
        return transport;
    }

    public Transport update(Transport transport, Long companyId) {
        var car = getCarForTransportIfBelongsToCompany(transport, companyId);
        var driver = getDriverForTransportIfBelongsToCompany(transport, companyId);
        var oldTransport = getTransportWithCarAndDriver(transport.getId());

        if (oldTransport.getStatus() != TransportStatusEnum.NotStarted) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The transport can't be updated because is in progress or is finished.");
        }

        transport.setStatus(oldTransport.getStatus());

        if (!Objects.equals(transport.getOriginAddress(), oldTransport.getOriginAddress()) ||
                !Objects.equals(transport.getDestinationAddress(), oldTransport.getDestinationAddress())) {
            try {
                var route = routeApiService.calculateRoute(transport.getOriginAddress(), transport.getDestinationAddress());
                if (oldTransport.getRoute() == null) {
                    route.setTransport(transport);
                    transport.setRoute(route);
                } else {
                    transport.setRoute(oldTransport.getRoute());
                    transport.getRoute().setTotalDistance(route.getTotalDistance());
                    transport.getRoute().setTotalDuration(route.getTotalDuration());
                }
            } catch (JsonProcessingException ignored) {
            }
        }

        if (transport.getUser() != oldTransport.getUser()) {
            emailService.sendTransportHasBeenAssignedEmailToDriver(transport.getUser().getUsername(), transport);
        }

        return save(transport);
    }

    public void delete(Long id, Long companyId) {
        var transport = getTransportWithCompany(id);
        if (!Objects.equals(transport.getCar().getCompany().getId(), companyId)) {
            throw new TransportNotFoundException("Transport not found.");
        }
        delete(transport);
    }

    public List<Transport> filterByStatusAndUsername(TransportStatusEnum status, String username, Long companyId) {
        var carsOfTheCompany = carService.getCompanyCars(companyId);
        if (status != null && username != null) {
            var user = userService.findUser(username);
            return transportRepository.findAllByStatusAndUserAndCarIsIn(status, user, carsOfTheCompany);
        } else if (status != null) {
            return transportRepository.findAllByStatusAndCarIsIn(status, carsOfTheCompany);
        } else {
            var user = userService.findUser(username);
            return transportRepository.findAllByUserAndCarIsIn(user, carsOfTheCompany);
        }
    }

    public Collection<TransportAction> getActions(Long transportId, Long companyId) {
        var transport = getTransportWithCompany(transportId);
        if (!Objects.equals(transport.getCar().getCompany().getId(), companyId)) {
            throw new TransportNotFoundException("Transport not found.");
        }
        return transportActionService.getActionsForTransport(transport);
    }

    public void addTransportAction(TransportAction transportAction, String username) {
        var transport = getTransportWithUserAndActions(transportAction.getTransport().getId());

        if (!Objects.equals(transport.getUser().getUsername(), username)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Transport is not assigned to you.");
        }

        switch (transportAction.getType()) {
            case Start -> {
                if (transport.getStatus() != TransportStatusEnum.NotStarted) {
                    throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Invalid action for the transport.");
                }
                var transportActions = new ArrayList<TransportAction>();
                transportActions.add(transportAction);
                transport.setStartDate(LocalDateTime.now());
                transport.setStatus(TransportStatusEnum.Started);
                transport.setTransportActions(transportActions);
            }
            case StartRefill, FinishRefill -> {
                if (transport.getStatus() != TransportStatusEnum.Started) {
                    throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Invalid action for the transport.");
                }
                var transportActions = transport.getTransportActions();
                transportActions.add(transportAction);
                transport.setTransportActions(transportActions);
            }
            case Finish -> {
                if (transport.getStatus() != TransportStatusEnum.Started) {
                    throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Invalid action for the transport.");
                }
                var transportActions = transport.getTransportActions();
                transportActions.add(transportAction);
                transport.setFinishDate(LocalDateTime.now());
                transport.setStatus(TransportStatusEnum.Finished);
                transport.setTransportActions(transportActions);
            }
            default -> {
            }
        }

        save(transport);

        if (transportAction.getType() == TransportActionTypeEnum.Finish) {
            var company = transport.getUser().getCompany();
            var companyManager = userService.getCompanyManager(company);
            emailService.sendTransportHasFinishedEmailToManager(companyManager.getUsername(), transport);
        } else if (transportAction.getType() == TransportActionTypeEnum.Start) {
            var company = transport.getUser().getCompany();
            var companyManager = userService.getCompanyManager(company);
            emailService.sendTransportHasStartedEmailToManager(companyManager.getUsername(), transport);
        }

    }


}

