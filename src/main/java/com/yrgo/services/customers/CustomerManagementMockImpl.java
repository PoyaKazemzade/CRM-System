package com.yrgo.services.customers;

import java.util.HashMap;
import java.util.List;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;

public class CustomerManagementMockImpl implements CustomerManagementService {

    private HashMap<String, Customer> customerMap;

    public CustomerManagementMockImpl() {
        customerMap = new HashMap<String, Customer>();
        customerMap.put("OB74", new Customer("OB74", "Fargo Ltd", "some notes"));
        customerMap.put("NV10", new Customer("NV10", "North Ltd", "some other notes"));
        customerMap.put("RM210", new Customer("RM210", "River Ltd", "some more notes"));
    }


    @Override
    public void newCustomer(Customer newCustomer) {
        if (!customerMap.containsKey(newCustomer.getCustomerId()))
            customerMap.put(newCustomer.getCustomerId(), newCustomer);

    }

    @Override
    public void updateCustomer(Customer changedCustomer) {
        if (customerMap.containsKey(changedCustomer.getCustomerId()))
            customerMap.replace(changedCustomer.getCustomerId(), changedCustomer);

    }

    @Override
    public void deleteCustomer(Customer oldCustomer) {
        customerMap.remove(oldCustomer.getCustomerId());
    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException {

        if (!customerMap.containsKey(customerId))
            throw new CustomerNotFoundException();

        return customerMap.get(customerId);
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return customerMap.values().stream().filter(x -> x.getCompanyName().equalsIgnoreCase(name)).toList();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerMap.values().stream().toList();
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {

        if (!customerMap.containsKey(customerId))
            throw new CustomerNotFoundException();

        var customer = customerMap.get(customerId);

        return new Customer(customer.getCustomerId(), customer.getCompanyName(),
                customer.getEmail(), customer.getTelephone(), customer.getNotes());
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        if (!customerMap.containsKey(customerId))
            throw new CustomerNotFoundException();

        if (callDetails == null)
            throw new NullPointerException("Call details are missing!");
        //First find the customer
        var customer = customerMap.get(customerId);
        //Call the addCall on the customer
        customer.addCall(callDetails);

    }

}
