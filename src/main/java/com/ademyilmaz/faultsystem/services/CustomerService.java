package com.ademyilmaz.faultsystem.services;

import com.ademyilmaz.faultsystem.entities.Customer;
import com.ademyilmaz.faultsystem.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Optional<Customer> getByUuid(UUID customerUUID) {
        return customerRepository.findByUuid(customerUUID);
    }

    @Transactional
    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}


