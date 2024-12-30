package com.ademyilmaz.faultsystem;

import com.ademyilmaz.faultsystem.controllers.FaultController;
import com.ademyilmaz.faultsystem.entities.Customer;
import com.ademyilmaz.faultsystem.entities.Fault;
import com.ademyilmaz.faultsystem.enums.FaultType;
import com.ademyilmaz.faultsystem.enums.StatusType;
import com.ademyilmaz.faultsystem.models.FaultCancelModel;
import com.ademyilmaz.faultsystem.models.FaultCreateModel;
import com.ademyilmaz.faultsystem.models.FaultSuccessModel;
import com.ademyilmaz.faultsystem.services.CustomerService;
import com.ademyilmaz.faultsystem.services.FaultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class FaultSystemApplicationTests {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private FaultService faultService;
    @Autowired
    private FaultController faultController;

    private final UUID defaultCustomerUUID =  UUID.fromString("e61b6fbc-9d1e-46bb-88de-16af5766f6e6");
    private final UUID defaultFaultUUID = UUID.fromString("31f5f92e-4d53-4874-80d9-49aaed6f3b70");
    private final Customer defaultCustomer = new Customer();
    private final Fault defaultFault = new Fault();

    private static final String NO_FAULT_FOUND_WITH_GIVEN_ID = "No fault found with given id";
    private static final String NO_CUSTOMER_FOUND_WITH_GIVEN_ID = "No Customer found with given id";
    private static final String STATUS = "status";
    private static final String REASON = "reason";

    @BeforeEach
    void setUp() {
        defaultCustomer.setUuid(defaultCustomerUUID);
        defaultCustomer.setFirstName("Adem");
        defaultCustomer.setLastName("YILMAZ");
        customerService.save(defaultCustomer);

        defaultFault.setUuid(defaultFaultUUID);
        defaultFault.setCustomer(defaultCustomer);
        defaultFault.setFaultType(FaultType.HARDWARE);
        defaultFault.setDescription("Description1");
        defaultFault.setCancelReason("Reason1");
        defaultFault.setSatisfactionScore(3);
        faultService.save(defaultFault);

    }

    @Test
    void testCreateFault_Success() {
        FaultCreateModel model = new FaultCreateModel();
        model.setCustomerUUID(defaultCustomerUUID);
        model.setFaultType(FaultType.HARDWARE);
        model.setDescription("Description1");

        ResponseEntity<?> response = faultController.create(model);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateFault_CustomerNotFound() {
        FaultCreateModel model = new FaultCreateModel();
        model.setCustomerUUID(UUID.randomUUID());

        ResponseEntity<?> response = faultController.create(model);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), Map.of(STATUS, StatusType.FAIL.name(), REASON, NO_CUSTOMER_FOUND_WITH_GIVEN_ID));
    }

    @Test
    void testUpdateFault_Success() {
        FaultCreateModel model = new FaultCreateModel();
        model.setCustomerUUID(defaultCustomerUUID);
        model.setFaultType(FaultType.SOFTWARE);
        model.setDescription("Description2");

        ResponseEntity<?> response = faultController.update(defaultFaultUUID, model);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), Map.of(STATUS, StatusType.SUCCESS.name()));
    }

    @Test
    void testUpdateFault_FaultNotFound() {
        UUID faultUUID = UUID.randomUUID();
        FaultCreateModel model = new FaultCreateModel();
        model.setCustomerUUID(UUID.randomUUID());

        ResponseEntity<?> response = faultController.update(faultUUID, model);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), Map.of(STATUS, StatusType.FAIL.name(), REASON, NO_FAULT_FOUND_WITH_GIVEN_ID));
    }

    @Test
    void testCancelFault_Success() {
        FaultCancelModel model = new FaultCancelModel();
        model.setCustomerUUID(defaultCustomerUUID);

        ResponseEntity<?> response = faultController.cancel(defaultFaultUUID, model);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), Map.of(STATUS, StatusType.SUCCESS.name()));
    }

    @Test
    void testCancelFault_FaultNotFound() {
        UUID faultUUID = UUID.randomUUID();
        FaultCancelModel model = new FaultCancelModel();
        model.setCustomerUUID(UUID.randomUUID());

        ResponseEntity<?> response = faultController.cancel(faultUUID, model);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), Map.of(STATUS, StatusType.FAIL.name(), REASON, NO_FAULT_FOUND_WITH_GIVEN_ID));
    }

    @Test
    void testDoSuccessFault_Success() {
        FaultSuccessModel model = new FaultSuccessModel();
        model.setCustomerUUID(defaultCustomerUUID);
        model.setSatisfactionScore(5);

        ResponseEntity<?> response = faultController.doSuccess(defaultFaultUUID, model);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), Map.of(STATUS, StatusType.SUCCESS.name()));
    }

    @Test
    void testDoSuccessFault_FaultNotFound() {
        UUID faultUUID = UUID.randomUUID();
        FaultSuccessModel model = new FaultSuccessModel();
        model.setCustomerUUID(UUID.randomUUID());

        ResponseEntity<?> response = faultController.doSuccess(faultUUID, model);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), Map.of(STATUS, StatusType.FAIL.name(), REASON, NO_FAULT_FOUND_WITH_GIVEN_ID));

    }

    @Test
    void testGetAllFaultsByCustomer_Success() {
        ResponseEntity<?> response = faultController.getAllFaultsByCustomer(defaultCustomerUUID);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertInstanceOf(List.class, response.getBody());
        assertTrue(((List<?>) response.getBody()).stream().allMatch(Fault.class::isInstance));

    }

    @Test
    void testGetAllFaultsByCustomer_CustomerNotFound() {
        UUID customerUUID = UUID.randomUUID();

        ResponseEntity<?> response = faultController.getAllFaultsByCustomer(customerUUID);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), Map.of(STATUS, StatusType.FAIL.name(), REASON, NO_CUSTOMER_FOUND_WITH_GIVEN_ID));
    }

}
