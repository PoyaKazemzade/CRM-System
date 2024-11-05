package com.yrgo.client;

import com.yrgo.domain.Customer;
import com.yrgo.services.customers.CustomerManagementMockImpl;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleClient {
    public static void main(String[] args) throws CustomerNotFoundException {
        ClassPathXmlApplicationContext container = new
                ClassPathXmlApplicationContext("application.xml");

        CustomerManagementService customerManagementService = container.getBean(CustomerManagementMockImpl.class);


        var allCustomers = customerManagementService.getAllCustomers();

        for (Customer customer : allCustomers)
            System.out.println(customer);


        container.close();
    }
}
