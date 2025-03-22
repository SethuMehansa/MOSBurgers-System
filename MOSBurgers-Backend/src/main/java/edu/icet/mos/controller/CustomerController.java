package edu.icet.mos.controller;

import edu.icet.mos.dto.Customer;
import edu.icet.mos.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@CrossOrigin
public class CustomerController {

    final CustomerService customerService;

    @PostMapping("/add-customer")
    public void addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
    }

    @GetMapping("/get-all")
    public List<Customer> getAllCustomers() {
        return customerService.getAll();
    }

    @PutMapping("/update-customer/{id}")
    public void updateCustomer(@RequestBody Customer customer) {
        customerService.updateCustomer(customer);
    }

    @DeleteMapping("/delete-customer/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

//    @GetMapping("/get-customer-by-contact/{contact}")
//    public Customer getCustomerByContact(@PathVariable String contact) {
//        return customerService.getCustomerByContact(contact);
//    }
//    @GetMapping("/get-customer-by-name/{name}")
//    public List<Customer> getCustomerByName(@PathVariable String name) {
//        return customerService.getCustomerByName(name);
//    }
}