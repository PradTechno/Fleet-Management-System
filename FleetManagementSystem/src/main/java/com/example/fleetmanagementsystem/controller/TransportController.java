package com.example.fleetmanagementsystem.controller;

import com.example.fleetmanagementsystem.domain.TransportStatusEnum;
import com.example.fleetmanagementsystem.dto.TransportActionDto;
import com.example.fleetmanagementsystem.dto.TransportDto;
import com.example.fleetmanagementsystem.mapper.TransportActionMapper;
import com.example.fleetmanagementsystem.mapper.TransportMapper;
import com.example.fleetmanagementsystem.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
public class TransportController {

    @Autowired
    private TransportMapper transportMapper;

    @Autowired
    private TransportService transportService;

    @Autowired
    private TransportActionMapper transportActionMapper;

    @PostMapping("/transport")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<TransportDto> addTransport(@Valid @RequestBody TransportDto transportDto, @AuthenticationPrincipal Jwt jwt) {
        Long companyId = Long.valueOf(jwt.getClaim("companyId"));
        return ResponseEntity.ok().body(transportMapper.mapToTransportDto(transportService.save(transportMapper.mapToTransport(transportDto), companyId)));
    }

    @PutMapping("/transport")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<TransportDto> updateTransport(@Valid @RequestBody TransportDto transportDto, @AuthenticationPrincipal Jwt jwt) {
        if (transportDto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specify the id of the transport.");
        }
        Long companyId = Long.valueOf(jwt.getClaim("companyId"));
        return ResponseEntity.ok().body(transportMapper.mapToTransportDto(transportService.update(transportMapper.mapToTransport(transportDto), companyId)));
    }

    @GetMapping("/transport/{id}/actions")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<Collection<TransportActionDto>> getTransportActions(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Long companyId = Long.valueOf(jwt.getClaim("companyId"));
        var actions = transportService.getActions(id, companyId);
        return ResponseEntity.ok().body(actions.stream().map(a -> transportActionMapper.mapToTransportActionDto(a)).sorted(Comparator.comparing(TransportActionDto::getCreatedDate)).collect(Collectors.toList()));
    }

    @DeleteMapping("/transport/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity deleteTransport(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Long companyId = Long.valueOf(jwt.getClaim("companyId"));
        transportService.delete(id, companyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/transport")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<Collection<TransportDto>> getTransports(@RequestParam(required = false) TransportStatusEnum status, @RequestParam(required = false) String username, @AuthenticationPrincipal Jwt jwt) {
        if (status == null && username == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specify at least one of the following: status and username.");
        }
        Long companyId = Long.valueOf(jwt.getClaim("companyId"));
        var transports = transportService.filterByStatusAndUsername(status, username, companyId);
        return ResponseEntity.ok().body(transports.stream().map(t -> transportMapper.mapToTransportDto(t)).collect(Collectors.toList()));
    }

    @GetMapping("/driver/transport")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity<Collection<TransportDto>> getDriverTransports(@RequestParam(required = false) TransportStatusEnum status, Principal principal, @AuthenticationPrincipal Jwt jwt) {
        Long companyId = Long.valueOf(jwt.getClaim("companyId"));
        var transports = transportService.filterByStatusAndUsername(status, principal.getName(), companyId);
        return ResponseEntity.ok().body(transports.stream().map(t -> transportMapper.mapToTransportDto(t)).collect(Collectors.toList()));
    }

    @PostMapping("/driver/transport")
    @PreAuthorize("hasAuthority('ROLE_DRIVER')")
    public ResponseEntity addTransportAction(@Valid @RequestBody TransportActionDto transportActionDto, Principal principal) {
        transportService.addTransportAction(transportActionMapper.mapToTransportAction(transportActionDto), principal.getName());
        return ResponseEntity.ok().build();
    }
}
