package com.example.fleetmanagementsystem.exception;


public class TransportActionNotFoundException extends RuntimeException {
    public TransportActionNotFoundException(String message) {
        super(message);
    }
}