package edu.school21.models;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    @Test
    public void testGettersAndSetters() {
        User user = new User(null, null, null, false);
        Long expectedId = 1L;
        String expectedLogin = "testUser";
        String expectedPassword = "securePass123";
        boolean expectedAuthStatus = true;

        user.setId(expectedId);
        user.setLogin(expectedLogin);
        user.setPassword(expectedPassword);
        user.setAuthenticated(expectedAuthStatus);
        assertEquals(expectedId, user.getId());
        assertEquals(expectedLogin, user.getLogin());
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedAuthStatus, user.isAuthenticated());
    }

    @Test
    public void testConstructor() {
        Long expectedId = 1L;
        String expectedLogin = "testUser";
        String expectedPassword = "securePass123";
        boolean expectedAuthStatus = true;

        User user = new User(expectedId, expectedLogin, expectedPassword, expectedAuthStatus);
        assertEquals(expectedId, user.getId());
        assertEquals(expectedLogin, user.getLogin());
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedAuthStatus, user.isAuthenticated());
    }
}
