package com.example.fleetmanagementsystem.exception;

public class CarMaintenanceNotFoundException extends RuntimeException {
    public CarMaintenanceNotFoundException(String message) {
        super(message);
    }
}