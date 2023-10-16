package br.com.wswork.bestcommerceapi.controller;

import br.com.wswork.bestcommerceapi.dto.StoreDTO;
import br.com.wswork.bestcommerceapi.exception.StoreAlreadyExistsException;
import br.com.wswork.bestcommerceapi.exception.StoreNotFoundException;
import br.com.wswork.bestcommerceapi.model.Store;
import br.com.wswork.bestcommerceapi.service.StoreService;
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
@RequestMapping("/api/v1/best-commerce/store")
@Tag(name = "/api/v1/best-commerce/store")
public class StoreController {

    private final StoreService storeService;

    @Operation(summary = "Get all stores", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @GetMapping(value = "/all", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getAllStores() {
        try {
            List<Store> stores = storeService.getAllStores();
            return new ResponseEntity<>(StoreDTO.convertListEntityToDTO(stores), HttpStatus.OK);
        }
        catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get store by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Store not found")})
    @GetMapping(value = "/id/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getStoreById(
            @Parameter(description = "The ID of the store to retrieve")
            @PathVariable Long id) {
        try {
            Store store = storeService.getStoreById(id);
            return new ResponseEntity<>(StoreDTO.convertEntityToDTO(store), HttpStatus.OK);
        }
        catch (StoreNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get store by name", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Store not found")})
    @GetMapping(value = "/name/{name}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getStoreByName(
            @Parameter(description = "The name of the store to retrieve")
            @PathVariable String name) {
        try {
            Store store = storeService.getStoreByName(name);
            return new ResponseEntity<>(StoreDTO.convertEntityToDTO(store), HttpStatus.OK);
        }
        catch (StoreNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new store", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Store created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")})
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addStore(
            @Valid @Parameter(description = "Store to be added")
            @RequestBody Store newStore) {
        try {
            Store store = storeService.addStore(newStore);
            return new ResponseEntity<>(StoreDTO.convertEntityToDTO(store), HttpStatus.CREATED);
        }
        catch (StoreAlreadyExistsException exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Modify store by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store modified successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Store not found")})
    @PutMapping(value = "/modify/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> modifyStore(
            @Parameter(description = "Store to be modified")
            @Valid @RequestBody Store modifiedStore,
            @PathVariable Long id) {
        try {
            Store store = storeService.modifyStore(modifiedStore, id);
            return new ResponseEntity<>(StoreDTO.convertEntityToDTO(store), HttpStatus.OK);
        }
        catch (StoreNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete store by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Store not found")})
    @DeleteMapping(value = "/delete/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteStoreById(
            @Parameter(description = "The ID of the store to delete")
            @PathVariable Long id) {
        try {
            storeService.deleteStoreById(id);
            return new ResponseEntity<>("Store with id " + id + " deleted successfully.", HttpStatus.OK);
        }
        catch (StoreNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}