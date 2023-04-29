package com.zjkung;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("")
public class Main {
    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public record NewCustomerRequest(String name, String email, Integer age) {
    }

    @PostMapping("customers")
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        var newCustomer = new Customer();
        newCustomer.setAge(request.age);
        newCustomer.setEmail(request.email);
        newCustomer.setName(request.name);
        customerRepository.save(newCustomer);
    }

    @DeleteMapping("customer/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        customerRepository.deleteById(id);
    }

    @PutMapping("customer/{id}")
    public void updateCustomer(@PathVariable Integer id, @RequestBody NewCustomerRequest request) {
        var customer = customerRepository.findById(id);
        if (!customer.isPresent()) {
            return;
        }
        var updateCustomer = customer.get();
        updateCustomer.setAge(request.age);
        updateCustomer.setEmail(request.email);
        updateCustomer.setName(request.name);
        customerRepository.save(updateCustomer);
    }
}
