package edu.icet.crn.service.impl;

import edu.icet.crn.dto.Customer;
import edu.icet.crn.entity.CustomerEntity;
import edu.icet.crn.repository.CustomerRepository;
import edu.icet.crn.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository customerRepository;
    final ModelMapper modelMapper;

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(modelMapper.map(customer, CustomerEntity.class));
    }

    @Override
    public List<Customer> getAll() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();
        customerEntities.forEach(customerEntity -> customers.add(modelMapper.map(customerEntity, Customer.class)));
        return customers;
    }

    @Override
    public Customer getCustomerByContact(String contactNumber) {
        return customerRepository.findByContactNumber(contactNumber)
                .map(customerEntity -> modelMapper.map(customerEntity, Customer.class))
                .orElse(null);
    }

    @Override
    public List<Customer> getCustomerByName(String name) {
        List<CustomerEntity> customerEntities = customerRepository.findByNameContainingIgnoreCase(name);
        List<Customer> customers = new ArrayList<>();
        customerEntities.forEach(customerEntity -> customers.add(modelMapper.map(customerEntity, Customer.class)));
        return customers;
    }


    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(modelMapper.map(customer, CustomerEntity.class));
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}