package edu.icet.mos.service;

import edu.icet.mos.dto.Customer;

import java.util.List;

public interface CustomerService {
    void addCustomer(Customer customer);
    List<Customer> getAll();
//    Customer getCustomerByContact(String contactNumber);
//    List<Customer> getCustomerByName(String name);
    void updateCustomer(Customer customer);
    void deleteCustomer(Long id);}