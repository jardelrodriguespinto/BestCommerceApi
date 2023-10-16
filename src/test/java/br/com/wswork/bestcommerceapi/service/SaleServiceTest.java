package br.com.wswork.bestcommerceapi.service;

import br.com.wswork.bestcommerceapi.exception.SaleNotFoundException;
import br.com.wswork.bestcommerceapi.model.*;
import br.com.wswork.bestcommerceapi.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SaleServiceTest {

    @InjectMocks
    private SaleService saleService;

    @Mock
    private SaleRepository saleRepository;

    @BeforeEach
    public void setUp() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", "Description 1", new Category(1L, "Category 1"), new BigDecimal( "10.0"), 0.1),
                new Product(2L, "Product 2", "Description 2", new Category(1L, "Category 1"), new BigDecimal("15.0"), 0.1)
        );

        when(saleRepository.findAll()).thenReturn(Arrays.asList(
                new Sale(1L, new Customer(1L, "John", "Doe", 30, new CustomerType(null, "Owner"),
                                        new Address(null, 0, null, null, null, null)), new Store(), products, LocalDateTime.now()),
                new Sale(2L, new Customer(1L, "John", "Doe", 30, new CustomerType(null, "Owner"),
                                        new Address(null, 0, null, null, null, null)), new Store(), products, LocalDateTime.now())
        ));

        when(saleRepository.findById(1L)).thenReturn(Optional.of(
                new Sale(1L, new Customer(1L, "John", "Doe", 30, new CustomerType(null, "Owner"),
                                        new Address(null, 0, null, null, null, null)), new Store(), products, LocalDateTime.now())
        ));

        when(saleRepository.findById(3L)).thenReturn(Optional.empty());
    }

    @Test
    public void testGetAllSales() {
        List<Sale> sales = saleService.getAllSales();
        assertEquals(2, sales.size());
    }

    @Test
    public void testGetSaleById() {
        Sale sale = saleService.getSaleById(1L);
        assertNotNull(sale);
        assertEquals(1L, sale.getId());
    }

    @Test
    public void testGetSaleById_WhenSaleNotFound() {
        assertThrows(SaleNotFoundException.class, () -> saleService.getSaleById(3L));
    }

    @Test
    public void testAddSale() {
        List<Product> newProducts = new ArrayList<>();
        newProducts.add(new Product(3L, "Product 3", "Description 3", new Category(1L, "Category 1"), new BigDecimal("20.0"), 0.1));
        newProducts.add(new Product(4L, "Product 4", "Description 4", new Category(1L, "Category 1"), new BigDecimal("25.0"), 0.1));

        Sale newSale = new Sale(3L, new Customer(1L, "John", "Doe", 30, new CustomerType(null, "Owner"),
                        new Address(null, 0, null, null, null, null)), new Store(), newProducts, LocalDateTime.now());

        when(saleRepository.save(newSale)).thenReturn(newSale);

        Sale addedSale = saleService.addSale(newSale);

        assertNotNull(addedSale);
        assertEquals(3L, addedSale.getId());
    }

    @Test
    public void testModifySale() {
        List<Product> modifiedProducts = new ArrayList<>();
        modifiedProducts.add(new Product(1L, "Modified Product 1", "Modified Description 1", new Category(1L, "Category 1"), new BigDecimal("12.0"), 0.1));

        var customer = new Customer(1L, "John", "Doe", 30, new CustomerType(null, "Owner"),
                new Address(null, 0, null, null, null, null));

        Sale modifiedSale = new Sale(1L, customer, new Store(), modifiedProducts, LocalDateTime.now());
        when(saleRepository.findById(1L)).thenReturn(Optional.of(
                new Sale(1L, customer, new Store(), modifiedProducts, LocalDateTime.now())
        ));

        Sale result = saleService.modifySale(modifiedSale, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(customer, result.getCustomer());
    }

    @Test
    public void testModifySale_WhenSaleNotFound() {
        List<Product> modifiedProducts = new ArrayList<>();
        modifiedProducts.add(new Product(3L, "Modified Product 3", "Modified Description 3", new Category(1L, "Category 1"), new BigDecimal("22.0"), 0.1));

        Sale modifiedSale = new Sale(3L, new Customer(1L, "John", "Doe", 30, new CustomerType(null, "Owner"),
                        new Address(null, 0, null, null, null, null)), new Store(), modifiedProducts, LocalDateTime.now());
        when(saleRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(SaleNotFoundException.class, () -> saleService.modifySale(modifiedSale, 3L));
    }

    @Test
    public void testDeleteSaleById() {

        List<Product> products = new ArrayList<>();

        products.add(new Product(1L, "Product 1", "Description 1", new Category(1L, "Category 1"), new BigDecimal("10.0"), 0.1));
        products.add(new Product(2L, "Product 2", "Description 2", new Category(1L, "Category 1"), new BigDecimal("15.0"), 0.1));

        when(saleRepository.findById(1L)).thenReturn(Optional.of(
                new Sale(1L, new Customer(), new Store(), products, LocalDateTime.now())
        ));

        saleService.deleteSaleById(1L);

        Mockito.verify(saleRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteSaleById_WhenSaleNotFound() {
        when(saleRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(SaleNotFoundException.class, () -> saleService.deleteSaleById(3L));
    }
}