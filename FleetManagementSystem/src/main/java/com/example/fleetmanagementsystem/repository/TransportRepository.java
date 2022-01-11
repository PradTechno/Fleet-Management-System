package com.example.fleetmanagementsystem.repository;

import com.example.fleetmanagementsystem.domain.Car;
import com.example.fleetmanagementsystem.domain.Transport;
import com.example.fleetmanagementsystem.domain.TransportStatusEnum;
import com.example.fleetmanagementsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransportRepository extends JpaRepository<Transport, Long> {
    Transport save(Transport transport);

    @Query(value = "SELECT t from Transport t join fetch t.car where t.id = :id")
    Optional<Transport> findOneWithCar(Long id);

    @Query(value = "SELECT t FROM Transport t join fetch t.user join fetch t.user where t.id = :id")
    Optional<Transport> findOneWithCarAndDriver(Long id);

    @Query(value = "SELECT t FROM Transport t join fetch t.user left join fetch t.transportActions where t.id = :id")
    Optional<Transport> findOneWithDriverAndActions(Long id);

    Optional<Transport> findById(Long id);

    List<Transport> findAllByStatusAndUserAndCarIsIn(TransportStatusEnum status, User user, List<Car> cars);

    List<Transport> findAllByStatusAndCarIsIn(TransportStatusEnum status, List<Car> cars);

    List<Transport> findAllByUserAndCarIsIn(User user, List<Car> cars);

//    List<Transport> findAllByStatusAndUser(TransportStatusEnum status, User user);

    List<Transport> findAllByCar(Car car);
}