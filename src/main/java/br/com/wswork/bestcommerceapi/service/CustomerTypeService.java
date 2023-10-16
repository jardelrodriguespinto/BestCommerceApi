package br.com.wswork.bestcommerceapi.service;

import br.com.wswork.bestcommerceapi.exception.CustomerTypeAlreadyExistsException;
import br.com.wswork.bestcommerceapi.exception.CustomerTypeNotFoundException;
import br.com.wswork.bestcommerceapi.model.CustomerType;
import br.com.wswork.bestcommerceapi.repository.CustomerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerTypeService {
    private final CustomerTypeRepository customerTypeRepository;

    @Autowired
    public CustomerTypeService (CustomerTypeRepository customerTypeRepository) {
        this.customerTypeRepository = customerTypeRepository;
    }

    public List<CustomerType> getAllCustomerTypes() {

        return customerTypeRepository.findAll();
    }

    public CustomerType getCustomerTypeById(Long id) {

        Optional<CustomerType> customerType = customerTypeRepository.findById(id);

        return customerType.orElseThrow(() -> new CustomerTypeNotFoundException("CustomerType with id " + id + " not found."));
    }

    public CustomerType addCustomerType(CustomerType newCustomerType) {

        Optional<CustomerType> customerType = customerTypeRepository.findCustomerTypeByDescription(newCustomerType.getDescription());

        if (customerType.isPresent())
            throw new CustomerTypeNotFoundException("CustomerType with description " + newCustomerType.getDescription() + " already exists.");

        customerTypeRepository.save(newCustomerType);

        return newCustomerType;
    }

    public CustomerType modifyCustomerType(CustomerType modifiedCustomerType, Long id) {

        Optional<CustomerType> customerType = customerTypeRepository.findById(id);

        if (customerType.isEmpty())
            throw new CustomerTypeAlreadyExistsException("CustomerType with id " + id + " not found.");

        customerType.get().setId(modifiedCustomerType.getId());
        customerType.get().setDescription(modifiedCustomerType.getDescription());

        customerTypeRepository.save(customerType.get());

        return customerType.get();
    }

    public void deleteCustomerTypeById(Long id) {

        Optional<CustomerType> customerType = customerTypeRepository.findById(id);

        if (customerType.isEmpty())
            throw new CustomerTypeNotFoundException("CustomerType with id " + id + " not found.");

        customerTypeRepository.deleteById(id);
    }
}