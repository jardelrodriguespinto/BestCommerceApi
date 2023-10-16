package br.com.wswork.bestcommerceapi.service;

import br.com.wswork.bestcommerceapi.exception.CustomerAlreadyExistException;
import br.com.wswork.bestcommerceapi.exception.CustomerNotFoundException;
import br.com.wswork.bestcommerceapi.model.Customer;
import br.com.wswork.bestcommerceapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService (CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {

        Optional<Customer> customer = customerRepository.findById(id);

        return customer.orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found."));
    }

    public Customer addCustomer(Customer newCustomer) {

        Optional<Customer> customer = customerRepository.findCustomerByFullname(newCustomer.getFirstName(), newCustomer.getLastName());

        if (customer.isPresent())
            throw new CustomerAlreadyExistException("Customer " + newCustomer.getFirstName() + " " + newCustomer.getLastName() + " already exists.");

        customerRepository.save(newCustomer);

        return newCustomer;
    }

    public Customer modifyCustomer(Customer modifiedCustomer, Long id) {

        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty())
            throw new CustomerNotFoundException("Customer with id " + id + " not found.");

        customer.get().setId(modifiedCustomer.getId());
        customer.get().setFirstName(modifiedCustomer.getFirstName());
        customer.get().setLastName(modifiedCustomer.getLastName());
        customer.get().setAge(modifiedCustomer.getAge());
        customer.get().setAddress(modifiedCustomer.getAddress());
        customer.get().setCustomerType(modifiedCustomer.getCustomerType());

        customerRepository.save(customer.get());

        return customer.get();
    }

    public void deleteCustomerById(Long id) {

        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty())
            throw new CustomerNotFoundException("Category with id " + id + " not found.");

        customerRepository.deleteById(id);
    }
}