package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {
    private DataSource dataSource;
    private ProductsRepository repository;

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
      new Product(1L, "Laptop", 999.99),
      new Product(2L, "Smartphone", 699.99),
      new Product(3L, "Headphones", 149.99),
      new Product(4L, "Tablet", 399.99),
      new Product(5L, "Smartwatch", 199.99)
    );

    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "Laptop", 999.99);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(1L, "Ultrabook", 1299.99);
    final Product EXPECTED_SAVED_PRODUCT  = new Product(6L, "Monitor", 299.99);

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .ignoreFailedDrops(true)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
        repository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (dataSource instanceof EmbeddedDatabase) {
            ((EmbeddedDatabase) dataSource).shutdown();
        }
    }

    @Test
    @DisplayName("Test findAll()")
    void testFindAll() {
        List<Product> products = repository.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, products);
        assertIterableEquals(EXPECTED_FIND_ALL_PRODUCTS, products);
    }

    @Test
    @DisplayName("Test findById() with existing ID")
    void testFindByIdExisting() {
        Optional<Product> actual = repository.findById(1L);
        assertTrue(actual.isPresent());
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, actual.get());
    }

    @Test
    @DisplayName("Test findById() with non-existing ID")
    void testFindByIdNonExisting() {
        Optional<Product> actual = repository.findById(100L);
        assertFalse(actual.isPresent());
    }

    @Test
    @DisplayName("Test update()")
    void testUpdate() {
        repository.update(EXPECTED_UPDATED_PRODUCT);
        Optional<Product> actual = repository.findById(1L);
        assertTrue(actual.isPresent());
        assertEquals(EXPECTED_UPDATED_PRODUCT.getName(), actual.get().getName());
        assertEquals(EXPECTED_UPDATED_PRODUCT.getPrice(), actual.get().getPrice());
    }

    @Test
    @DisplayName("Test save()")
    void testSave() {
        repository.save(EXPECTED_SAVED_PRODUCT);
        List<Product> products = repository.findAll();
        assertEquals(6, products.size());

        Optional<Product> savedProduct = products.stream()
                .filter(p -> p.getName().equals(EXPECTED_SAVED_PRODUCT.getName()))
                .findFirst();
        assertTrue(savedProduct.isPresent());
        assertEquals(EXPECTED_SAVED_PRODUCT.getPrice(), savedProduct.get().getPrice());
    }

    @Test
    @DisplayName("Test delete()")
    void testDelete() {
        repository.delete(1L);
        Optional<Product> actual = repository.findById(1L);
        assertFalse(actual.isPresent());
        assertEquals(4, repository.findAll().size());
    }
}
