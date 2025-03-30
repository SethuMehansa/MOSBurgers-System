package edu.icet.mos.service.impl;

import edu.icet.mos.dto.Customer;
import edu.icet.mos.entity.CustomerEntity;
import edu.icet.mos.repository.CustomerRepository;
import edu.icet.mos.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

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
    public void updateCustomer(Customer customer) {
        customerRepository.save(modelMapper.map(customer, CustomerEntity.class));
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}