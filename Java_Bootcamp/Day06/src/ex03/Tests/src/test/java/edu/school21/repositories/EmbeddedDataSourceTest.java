package edu.school21.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class EmbeddedDataSourceTest {
    private DataSource dataSource;

    @BeforeEach
    void init() throws SQLException {
        this.dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .setSeparator(";")
                .build();
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (dataSource instanceof EmbeddedDatabase) {
            ((EmbeddedDatabase) dataSource).shutdown();
        }
    }

    @Test
    @DisplayName("Test that we can get a connection")
    void testGetConnection() throws SQLException {
        try (Connection conn = dataSource.getConnection();) {
            assertNotNull(conn, "Connection should not be null");
            assertFalse(conn.isClosed(), "Connection should be open");
        }
    }

    @Test
    @DisplayName("Test that our test data was loaded correctly")
    void testProductData() throws SQLException {
        try (Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from products where identifier = 1")) {
            assertTrue(rs.next());
            assertEquals("Laptop", rs.getString("name"), "Should find the Laptop product");
            assertEquals(999.99, rs.getDouble("price"), 0.0001);
        }

    }
}
