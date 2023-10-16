package br.com.wswork.bestcommerceapi.controller;

import br.com.wswork.bestcommerceapi.dto.SaleDTO;
import br.com.wswork.bestcommerceapi.exception.SaleNotFoundException;
import br.com.wswork.bestcommerceapi.model.Sale;
import br.com.wswork.bestcommerceapi.service.SaleService;
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
@RequestMapping("/api/v1/best-commerce/sale")
@Tag(name = "/api/v1/best-commerce/sale")
public class SaleController {

    private final SaleService saleService;

    @Operation(summary = "Get all sales", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @GetMapping(value = "/all", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getAllSales() {
        try {
            List<Sale> sales = saleService.getAllSales();
            return new ResponseEntity<>(SaleDTO.convertListEntityToDTO(sales), HttpStatus.OK);
        }
        catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get all sales by customer ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @GetMapping(value = "/all/customer/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getAllSalesByCustomer(
            @Parameter(description = "The ID of the customer for which to retrieve sales")
            @PathVariable Long id) {
        try {
            List<Sale> sales = saleService.getAllSalesByCustomer(id);
            return new ResponseEntity<>(SaleDTO.convertListEntityToDTO(sales), HttpStatus.OK);
        }
        catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get sale by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Sale not found")})
    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getSaleById(
            @Parameter(description = "The ID of the sale to retrieve")
            @PathVariable Long id) {
        try {
            Sale sale = saleService.getSaleById(id);
            return new ResponseEntity<>(SaleDTO.convertEntityToDTO(sale), HttpStatus.OK);
        }
        catch (SaleNotFoundException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new sale", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sale created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),})
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addSale(
            @Parameter(description = "Sale to added")
            @Valid @RequestBody Sale newSale) {
        try {
            Sale sale = saleService.addSale(newSale);
            return new ResponseEntity<>(SaleDTO.convertEntityToDTO(sale), HttpStatus.CREATED);
        }
        catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Modify sale by ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale modified successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Sale not found")})
    @PutMapping(value = "/modify/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> modifySale(
            @Parameter(description = "Sale to be modified")
            @Valid @RequestBody Sale modifiedSale,
            @PathVariable Long id
    ) {
        try {
            Sale sale = saleService.modifySale(modifiedSale, id);
            return new ResponseEntity<>(SaleDTO.convertEntityToDTO(sale), HttpStatus.OK);
        }
        catch (SaleNotFoundException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete sale by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Sale not found")})
    @DeleteMapping(value = "/delete/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteSaleById(
            @Parameter(description = "The ID of the sale to delete")
            @PathVariable Long id) {
        try {
            saleService.deleteSaleById(id);
            return new ResponseEntity<>("Sale with ID " + id + " deleted successfully!", HttpStatus.OK);
        }
        catch (SaleNotFoundException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}