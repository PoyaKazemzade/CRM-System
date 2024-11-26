package com.yrgo.dataaccess;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerDaoJpaImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Customer customer) {
        em.persist(customer);
    }

    @Override
    public Customer getById(String customerId) throws RecordNotFoundException {
        try {
            return em.createQuery("select customer from Customer as customer where customer.customerId=:customerId", Customer.class).setParameter("customerId", customerId).getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public List<Customer> getByName(String name) {
        return em.createQuery("select customer from Customer as customer where customer.companyName=:name", Customer.class)
                .setParameter("name", name).getResultList();
    }

    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, customerToUpdate.getCustomerId());
        if (customer == null) {
            throw new RecordNotFoundException();
        }
        em.merge(customerToUpdate);
    }

    @Override
    public void delete(Customer oldCustomer) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, oldCustomer.getCustomerId());
        em.remove(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return em.createQuery("select customer from Customer as customer", Customer.class).getResultList();
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        try {
            return em.createQuery(
                            "SELECT customer FROM Customer as customer LEFT JOIN FETCH customer.calls WHERE customer.customerId = :customerId",
                            Customer.class)
                    .setParameter("customerId", customerId)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, customerId);
        if (customer == null) {
            throw new RecordNotFoundException();
        }
        customer.addCall(newCall);
        em.merge(customer);
    }

}
