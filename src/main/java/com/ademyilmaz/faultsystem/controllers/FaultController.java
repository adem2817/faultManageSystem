package com.ademyilmaz.faultsystem.controllers;


import com.ademyilmaz.faultsystem.entities.Customer;
import com.ademyilmaz.faultsystem.entities.Fault;
import com.ademyilmaz.faultsystem.enums.StatusType;
import com.ademyilmaz.faultsystem.models.FaultCancelModel;
import com.ademyilmaz.faultsystem.models.FaultCreateModel;
import com.ademyilmaz.faultsystem.models.FaultSuccessModel;
import com.ademyilmaz.faultsystem.services.CustomerService;
import com.ademyilmaz.faultsystem.services.FaultService;
import com.ademyilmaz.faultsystem.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/faults")
public class FaultController {

    private final CustomerService customerService;
    private final FaultService faultService;
    private static final String NO_FAULT_FOUND_WITH_GIVEN_ID = "No fault found with given id";
    private static final String NO_CUSTOMER_FOUND_WITH_GIVEN_ID = "No Customer found with given id";
    private static final String STATUS = "status";
    private static final String REASON = "reason";

    @PostMapping
    public ResponseEntity<?> create(@RequestBody FaultCreateModel faultCreateModel) {
        Optional<Customer> customer = customerService.getByUuid(faultCreateModel.getCustomerUUID());
        if (customer.isEmpty())
            return ResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, NO_CUSTOMER_FOUND_WITH_GIVEN_ID);
        Fault fault = Fault.getFault(faultCreateModel, customer.get());
        return ResponseEntity.ok(Map.of("id", faultService.save(fault)));
    }

    @PutMapping(value = "/{fault-id}")
    public ResponseEntity<?> update(@PathVariable("fault-id") UUID faultUUID,
                                    @RequestBody FaultCreateModel faultCreateModel) {
        Fault fault = faultService.getByFaultUuidAndCustomerUuid(faultUUID, faultCreateModel.getCustomerUUID()).orElse(null);
        if (fault == null)
            return ResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, NO_FAULT_FOUND_WITH_GIVEN_ID);
        fault.setFaultType(faultCreateModel.getFaultType());
        fault.setDescription(faultCreateModel.getDescription());
        UUID faultUuid = faultService.save(fault);
        if (faultUuid == null)
            return ResponseEntity.internalServerError().body(StatusType.FAIL.name());
        return ResponseBuilder.buildSuccessResponse();
    }

    @PatchMapping("/{fault-id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable("fault-id") UUID faultUUID,
                                    @RequestBody FaultCancelModel faultCancelModel) {
        Fault fault = faultService.getByFaultUuidAndCustomerUuid(faultUUID, faultCancelModel.getCustomerUUID()).orElse(null);
        if (fault == null)
            return ResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, NO_FAULT_FOUND_WITH_GIVEN_ID);
        fault.setCancelReason(fault.getCancelReason());
        UUID faultUuid = faultService.save(fault);
        if (faultUuid == null)
            return ResponseEntity.internalServerError().body(StatusType.FAIL.name());
        return ResponseBuilder.buildSuccessResponse();
    }

    @PatchMapping("/{fault-id}/success")
    public ResponseEntity<?> doSuccess(@PathVariable("fault-id") UUID faultUUID,
                                       @Valid @RequestBody FaultSuccessModel faultSuccessModel) {
        Fault fault = faultService.getByFaultUuidAndCustomerUuid(faultUUID, faultSuccessModel.getCustomerUUID()).orElse(null);
        if (fault == null)
            return ResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, NO_FAULT_FOUND_WITH_GIVEN_ID);
        fault.setSatisfactionScore(faultSuccessModel.getSatisfactionScore());
        UUID faultUuid = faultService.save(fault);
        if (faultUuid == null)
            return ResponseEntity.internalServerError().body(StatusType.FAIL.name());
        return ResponseBuilder.buildSuccessResponse();
    }

    @GetMapping("/customer/{customer-uuid}")
    public ResponseEntity<?> getAllFaultsByCustomer(@PathVariable("customer-uuid") UUID customerUUID) {
        Optional<Customer> customer = customerService.getByUuid(customerUUID);
        if (customer.isEmpty())
            return ResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, NO_CUSTOMER_FOUND_WITH_GIVEN_ID);
        return ResponseEntity.ok(faultService.getAllFaultsByCustomerUuid(customerUUID));
    }
    
}


