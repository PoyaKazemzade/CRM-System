package com.yrgo.services.customers;

import java.util.List;

import com.yrgo.dataaccess.RecordNotFoundException;
import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;


public interface CustomerManagementService {

	public void newCustomer(Customer newCustomer);


	public void updateCustomer(Customer changedCustomer) throws RecordNotFoundException, CustomerNotFoundException;


	public void deleteCustomer(Customer oldCustomer) throws RecordNotFoundException, CustomerNotFoundException;


	public Customer findCustomerById(String customerId) throws CustomerNotFoundException, RecordNotFoundException;

	public List<Customer> findCustomersByName (String name) throws CustomerNotFoundException;

	public List<Customer> getAllCustomers();


	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException, RecordNotFoundException;

	
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException, RecordNotFoundException;
}
