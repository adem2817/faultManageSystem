package com.ademyilmaz.faultsystem.services;

import com.ademyilmaz.faultsystem.entities.Fault;
import com.ademyilmaz.faultsystem.repositories.FaultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FaultService {

    private final FaultRepository faultRepository;

    public Optional<Fault> getByUuid(UUID faultUUID) {
        return faultRepository.findByUuid(faultUUID);
    }

    @Transactional
    public UUID save(Fault fault) {
        try {
            return faultRepository.save(fault).getUuid();
        } catch (Exception e) {
            log.error("Error while saving fault", e);
            return null;
        }
    }

    public Optional<Fault> getByFaultUuidAndCustomerUuid(UUID faultUUID, UUID customerUUID) {
        return faultRepository.findByUuidAndCustomer_Uuid(faultUUID, customerUUID);
    }

    public List<Fault> getAllFaultsByCustomerUuid(UUID customerUUID) {
        return faultRepository.findAllByCustomer_Uuid(customerUUID);
    }
}
