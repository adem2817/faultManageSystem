package com.ademyilmaz.faultsystem.repositories;

import com.ademyilmaz.faultsystem.entities.Fault;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FaultRepository extends JpaRepository<Fault, Long> {

    Optional<Fault> findByUuid(UUID faultUUID);

    Optional<Fault> findByUuidAndCustomer_Uuid(UUID faultUUID, UUID customerUUID);

    List<Fault> findAllByCustomer_Uuid(UUID customerUUID);
}
