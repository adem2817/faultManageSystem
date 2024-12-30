package com.ademyilmaz.faultsystem.repositories;

import com.ademyilmaz.faultsystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUuid(UUID customerUUID);
}
