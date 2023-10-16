package br.com.wswork.bestcommerceapi.controller;

import br.com.wswork.bestcommerceapi.dto.CategoryDTO;
import br.com.wswork.bestcommerceapi.exception.CategoryAlreadyExitsException;
import br.com.wswork.bestcommerceapi.exception.CategoryNotFoundException;
import br.com.wswork.bestcommerceapi.model.Category;
import br.com.wswork.bestcommerceapi.service.CategoryService;
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
@RequestMapping("/api/v1/best-commerce/category")
@Tag(name = "/api/v1/best-commerce/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get all categories", method = "GET", description = "Retrieve a list of all categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")})
    @GetMapping(value = "/all", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return new ResponseEntity<>(CategoryDTO.convertListEntityToDTO(categories), HttpStatus.OK);
        }
        catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get category by ID", method = "GET", description = "Retrieve a category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Category not found")})
    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CategoryDTO> getCategoryById(
            @Parameter(description = "ID of the category to retrieve")
            @PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return new ResponseEntity<>(CategoryDTO.convertEntityToDTO(category), HttpStatus.OK);
        }
        catch (CategoryNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new category", method = "POST", description = "Create a new category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Category already exists")})
    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CategoryDTO> addCategory(
            @Parameter(description = "Category to be added")
            @Valid @RequestBody Category newCategory) {
        try {
            Category category = categoryService.addCategory(newCategory);
            return new ResponseEntity<>(CategoryDTO.convertEntityToDTO(category), HttpStatus.CREATED);
        }
        catch (CategoryAlreadyExitsException exc) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Modify category by ID", method = "PUT", description = "Update an existing category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category modified successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Category not found")})
    @PutMapping(value = "/modify/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CategoryDTO> modifyCategory(
            @Parameter(description = "Category to be modified")
            @Valid @RequestBody Category modifiedCategory,
            @Parameter(description = "ID of the category to be modified")
            @PathVariable Long id) {
        try {
            Category category = categoryService.modifyCategory(modifiedCategory, id);
            return new ResponseEntity<>(CategoryDTO.convertEntityToDTO(category), HttpStatus.OK);
        }
        catch (CategoryNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete category by ID", method = "DELETE", description = "Delete a category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Category not found")})
    @DeleteMapping(value = "/delete/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteCategoryById(
            @Parameter(description = "ID of the category to be deleted")
            @PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return new ResponseEntity<>("Category with ID " + id + " deleted successfully.", HttpStatus.OK);
        }
        catch (CategoryNotFoundException exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}