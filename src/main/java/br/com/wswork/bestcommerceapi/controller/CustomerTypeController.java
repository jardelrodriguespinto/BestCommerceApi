package br.com.wswork.bestcommerceapi.controller;

import br.com.wswork.bestcommerceapi.dto.CustomerTypeDTO;
import br.com.wswork.bestcommerceapi.exception.CustomerTypeAlreadyExistsException;
import br.com.wswork.bestcommerceapi.exception.CustomerTypeNotFoundException;
import br.com.wswork.bestcommerceapi.model.CustomerType;
import br.com.wswork.bestcommerceapi.service.CustomerTypeService;
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
@RequestMapping("/api/v1/best-commerce/customertype")
@Tag(name = "/api/v1/best-commerce/customertype")
public class CustomerTypeController {

    private final CustomerTypeService customerTypeService;

    @Operation(summary = "Get all customer types", description = "Get a list of all customer types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @GetMapping(value = "/all", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<CustomerTypeDTO>> getAllCustomerTypes() {
        try {
            List<CustomerType> customerTypes = customerTypeService.getAllCustomerTypes();
            return new ResponseEntity<>(CustomerTypeDTO.convertListEntityToDTO(customerTypes), HttpStatus.OK);
        }
        catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get customer type by ID", description = "Get a customer type by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Customer type not found")})
    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerTypeDTO> getCustomerTypeId(
            @Parameter(description = "ID of the customer type to retrieve")
            @PathVariable Long id) {
        try {
            CustomerType customerType = customerTypeService.getCustomerTypeById(id);
            return new ResponseEntity<>(CustomerTypeDTO.convertEntityToDTO(customerType), HttpStatus.OK);
        }
        catch (CustomerTypeNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new customer type", description = "Add a new customer type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer type created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "409", description = "Customer type already exists")})
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerTypeDTO> addCustomerType(
            @Parameter(description = "Customer type to be created")
            @Valid @RequestBody CustomerType newCustomerType) {
        try {
            CustomerType customerType = customerTypeService.addCustomerType(newCustomerType);
            return new ResponseEntity<>(CustomerTypeDTO.convertEntityToDTO(customerType), HttpStatus.CREATED);
        }
        catch (CustomerTypeAlreadyExistsException exc) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Modify customer type by ID", description = "Update an existing customer type by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer type modified successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Customer type not found")})
    @PutMapping(value = "/modify/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerTypeDTO> modifyCustomerType(
            @Parameter(description = "Customer type to be modified")
            @Valid @RequestBody CustomerType modifiedCustomerType,
            @Parameter(description = "ID of the customer type to be modified")
            @PathVariable Long id) {
        try {
            CustomerType customerType = customerTypeService.modifyCustomerType(modifiedCustomerType, id);
            return new ResponseEntity<>(CustomerTypeDTO.convertEntityToDTO(customerType), HttpStatus.OK);
        }
        catch (CustomerTypeNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete customer type by ID", description = "Delete a customer type by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer type deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Customer type not found")})
    @DeleteMapping(value = "/delete/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteCustomerTypeById(
            @Parameter(description = "ID of the customer type to delete")
            @PathVariable Long id) {
        try {
            customerTypeService.deleteCustomerTypeById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (CustomerTypeNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}