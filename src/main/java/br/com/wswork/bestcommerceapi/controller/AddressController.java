package br.com.wswork.bestcommerceapi.controller;

import br.com.wswork.bestcommerceapi.dto.AddressDTO;
import br.com.wswork.bestcommerceapi.exception.AddressAlreadyExitsException;
import br.com.wswork.bestcommerceapi.exception.AddressNotFoundException;
import br.com.wswork.bestcommerceapi.model.Address;
import br.com.wswork.bestcommerceapi.service.AddressService;
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
@RequestMapping("/api/v1/best-commerce/address")
@Tag(name = "/api/v1/best-commerce/address")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Get all addresses", method = "GET     ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @GetMapping(value = "/all", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getAllAddresses() {
        try {
            List<Address> addresses = addressService.getAllAddresses();
            return new ResponseEntity<>(AddressDTO.convertListEntityToDTO(addresses), HttpStatus.OK);
        }
        catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get address by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Address not found")})
    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getAddressById(
            @Parameter(description = "ID of the address to retrieve")
            @PathVariable Long id) {
        try {
            Address address = addressService.getAddressById(id);
            return new ResponseEntity<>(AddressDTO.convertEntityToDTO(address), HttpStatus.OK);
        }
        catch (AddressNotFoundException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new address", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Address already exists")})
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addAddress(
            @Parameter(description = "Address to be added")
            @Valid @RequestBody Address newAddress) {
        try {
            Address address = addressService.addAddress(newAddress);
            return new ResponseEntity<>(AddressDTO.convertEntityToDTO(address), HttpStatus.CREATED);
        }
        catch (AddressAlreadyExitsException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Modify address by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address modified successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Address not found")})
    @PutMapping(value = "/modify/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> modifyAddress(
            @Parameter(description = "Address to be modified")
            @Valid @RequestBody Address modifiedAddress,
            @Parameter(description = "ID of the address to modify")
            @PathVariable Long id) {
        try {
            Address address = addressService.modifyAddress(modifiedAddress, id);
            return new ResponseEntity<>(AddressDTO.convertEntityToDTO(address), HttpStatus.OK);
        }
        catch (AddressNotFoundException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete address by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Address not found")})
    @DeleteMapping(value = "/delete/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteAddressById(
            @Parameter(description = "ID of the address to delete")
            @PathVariable Long id) {
        try {
            addressService.deleteAddressById(id);
            return new ResponseEntity<>("Address with ID " + id + " deleted successfully.", HttpStatus.OK);
        }
        catch (AddressNotFoundException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}