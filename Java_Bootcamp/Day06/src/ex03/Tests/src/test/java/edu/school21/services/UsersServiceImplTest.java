package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {
    @Mock
    private UsersRepository usersRepository;

    @Test
    void testAuthenticateSucces() throws EntityNotFoundException {
        User user = new User(1L, "test", "password", false);
        when(usersRepository.findByLogin("test")).thenReturn(user);

        UsersServiceImpl service = new UsersServiceImpl(usersRepository);
        boolean result = service.authenticate("test", "password");
        assertTrue(result);
        assertTrue(user.isAuthenticated());
        verify(usersRepository).update(user);
    }

    @Test
    void testAuthenticateWrongPassword() throws EntityNotFoundException {
        User user = new User(1L, "test", "password", false);
        when(usersRepository.findByLogin("test")).thenReturn(user);

        UsersServiceImpl service = new UsersServiceImpl(usersRepository);
        boolean result = service.authenticate("test", "wrong");

        assertFalse(result);
        assertFalse(user.isAuthenticated());
        verify(usersRepository, never()).update(user);
    }

    @Test
    void testAuthenticateAlreadyAuthenticated() throws EntityNotFoundException {
        User user = new User(1L, "test", "password", true);
        when(usersRepository.findByLogin("test")).thenReturn(user);

        UsersServiceImpl service = new UsersServiceImpl(usersRepository);
        assertThrows(AlreadyAuthenticatedException.class, () -> {
            service.authenticate("test", "password");
        });
        verify(usersRepository, never()).update(user);
    }

    @Test
    void testAuthenticateUserNotFound() throws EntityNotFoundException {
        when(usersRepository.findByLogin("unknown")).thenThrow(
                new EntityNotFoundException("User not found"));

        UsersServiceImpl service = new UsersServiceImpl(usersRepository);
        assertThrows(EntityNotFoundException.class, () -> {
            service.authenticate("unknown", "password");
        });
    }

}
