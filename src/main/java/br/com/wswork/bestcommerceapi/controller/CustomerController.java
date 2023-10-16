package br.com.wswork.bestcommerceapi.controller;

import br.com.wswork.bestcommerceapi.dto.CustomerDTO;
import br.com.wswork.bestcommerceapi.exception.CustomerAlreadyExistException;
import br.com.wswork.bestcommerceapi.exception.CustomerNotFoundException;
import br.com.wswork.bestcommerceapi.model.Customer;
import br.com.wswork.bestcommerceapi.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/best-commerce/customer")
@Tag(name = "/api/v1/best-commerce/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Get all customers", method = "GET", description = "Retrieve a list of all customers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @GetMapping(value = "/all", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return new ResponseEntity<>(CustomerDTO.convertListEntityToDTO(customers), HttpStatus.OK);
        }
        catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get customer by ID", method = "GET", description = "Retrieve a customer by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Customer not found")})
    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerDTO> getCustomerById(
            @Parameter(description = "ID of the customer to retrieve")
            @PathVariable Long id) {
        try {
            Customer customer = customerService.getCustomerById(id);
            return new ResponseEntity<>(CustomerDTO.convertEntityToDTO(customer), HttpStatus.OK);
        }
        catch (CustomerNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new customer", method = "POST", description = "Create a new customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Customer already exists")})
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerDTO> addCustomer(
            @Parameter(description = "Customer to be added")
            @Valid @RequestBody Customer newCustomer) {
        try {
            Customer customer = customerService.addCustomer(newCustomer);
            return new ResponseEntity<>(CustomerDTO.convertEntityToDTO(customer), HttpStatus.CREATED);
        }
        catch (CustomerAlreadyExistException exc) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Modify customer by ID", method = "PUT", description = "Update an existing customer by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer modified successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Customer not found")})
    @PutMapping(value = "/modify/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerDTO> modifyCustomer(
            @Parameter(description = "Customer to be modified")
            @Valid @RequestBody Customer modifiedCustomer,
            @Parameter(description = "ID of the customer to be modified")
            @PathVariable Long id) {
        try {
            Customer customer = customerService.modifyCustomer(modifiedCustomer, id);
            return new ResponseEntity<>(CustomerDTO.convertEntityToDTO(customer), HttpStatus.OK);
        }
        catch (CustomerNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete customer by ID", method = "DELETE", description = "Delete a customer by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Customer not found")})
    @DeleteMapping(value = "/delete/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteCustomerById(
            @Parameter(description = "ID of the customer to be deleted")
            @PathVariable Long id) {
        try {
            customerService.deleteCustomerById(id);
            return new ResponseEntity<>("Customer with ID " + id + " deleted successfully.", HttpStatus.OK);
        }
        catch (CustomerNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
