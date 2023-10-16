package br.com.wswork.bestcommerceapi.controller;

import br.com.wswork.bestcommerceapi.dto.ProductDTO;
import br.com.wswork.bestcommerceapi.exception.ProductAlreadyExists;
import br.com.wswork.bestcommerceapi.exception.ProductNotFoundException;
import br.com.wswork.bestcommerceapi.model.Product;
import br.com.wswork.bestcommerceapi.service.ProductService;
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
@RequestMapping("/api/v1/best-commerce/product")
@Tag(name = "/api/v1/best-commerce/product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all products", description = "Get a list of all products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @GetMapping(value = "/all", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return new ResponseEntity<>(ProductDTO.convertListEntityToDTO(products), HttpStatus.OK);
        }
        catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get all products in a category", description = "Get a list of all products in a specific category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @GetMapping(value = "/all/categories", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getAllProductsPerCategory(@Parameter(description = "The name of the category") @RequestParam String category) {
        try {
            List<Product> products = productService.getAllProductsPerCategory(category);
            return new ResponseEntity<>(ProductDTO.convertListEntityToDTO(products), HttpStatus.OK);
        }
        catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get product by ID", description = "Get a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getProductById(@Parameter(description = "The ID of the product") @PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return new ResponseEntity<>(ProductDTO.convertEntityToDTO(product), HttpStatus.OK);
        }
        catch (ProductNotFoundException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get product by name", description = "Get a product by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @GetMapping(value = "/name/{name}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getProductByName(@Parameter(description = "The name of the product") @PathVariable String name) {
        try {
            Product product = productService.getProductByName(name);
            return new ResponseEntity<>(ProductDTO.convertEntityToDTO(product), HttpStatus.OK);
        }
        catch (ProductNotFoundException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new product", description = "Add a new product to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Product already exists")})
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addProduct(
            @Parameter(description = "Product to be added")
            @Valid @RequestBody Product newProduct
    ) {
        try {
            Product product = productService.addProduct(newProduct);
            return new ResponseEntity<>(ProductDTO.convertEntityToDTO(product), HttpStatus.CREATED);
        }
        catch (ProductAlreadyExists exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Modify product by ID", description = "Modify an existing product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product modified successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @PutMapping(value = "/modify/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> modifyProduct(
            @Parameter(description = "Product to be modified")
            @Valid @RequestBody Product modifiedProduct,
            @Parameter(description = "The ID of the product") @PathVariable Long id) {
        try {
            Product product = productService.modifyProduct(modifiedProduct, id);
            return new ResponseEntity<>(ProductDTO.convertEntityToDTO(product), HttpStatus.OK);
        }
        catch (ProductNotFoundException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete product by ID", description = "Delete a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Product not found")})
    @DeleteMapping(value = "/delete/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> deleteProductById(@Parameter(description = "The ID of the product") @PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
        }
        catch (ProductNotFoundException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}