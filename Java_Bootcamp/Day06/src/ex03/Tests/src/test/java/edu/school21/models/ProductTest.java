package edu.school21.models;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    @Test
    public void testEquals() {
        Product product1 = new Product(1L, "Laptop", 999.99);
        Product product2 = new Product(1L, "Laptop", 999.99);
        Product product3 = new Product(2L, "Phone", 699.99);
        Product product4 = null;
        String notAProduct = "Laptop";

        assertTrue(product1.equals(product1));
        assertTrue(product1.equals(product2));
        assertFalse(product1.equals(product3));
        assertFalse(product1.equals(product4));
        assertFalse(product1.equals(notAProduct));
    }
}
