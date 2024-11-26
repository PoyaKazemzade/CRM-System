package com.yrgo.integrationtests;

import com.yrgo.dataaccess.RecordNotFoundException;
import com.yrgo.domain.Customer;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@ContextConfiguration({"/other-tiers.xml", "/datasource-test.xml"})
@Transactional
public class CustomerManagementIntegrationTest {

    @Autowired
    private CustomerManagementService customerManagementService;

    @Test
    public void testFindingExistingCustomer() {

        // arrange
        String customerId = "2024";
        Customer testCustomer = new Customer(customerId, "FMS", "Hobby company");
        customerManagementService.newCustomer(testCustomer);

        // act
        Customer foundCustomer = null;
        try {
            foundCustomer = customerManagementService.findCustomerById(customerId);
            assertEquals(testCustomer, foundCustomer, "Customers are not the same!");
        } catch (CustomerNotFoundException | RecordNotFoundException e) {
            fail("Customer does not exist!");
        }
    }

    @Test
    public void testCreatingNewCustomer() {

        customerManagementService.newCustomer(new Customer("1", "FMS", "Hobby company"));
        customerManagementService.newCustomer(new Customer("2", "Traxxas", "Hobby company"));

        int numberOfCustomers = customerManagementService.getAllCustomers().size();
        assertEquals(2, numberOfCustomers, "There should be 2 customers in the database!");

    }
}
