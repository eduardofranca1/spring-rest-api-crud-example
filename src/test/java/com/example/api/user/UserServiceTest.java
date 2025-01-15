package com.example.api.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.api.entities.User;
import com.example.api.repositories.UserRepository;
import com.example.api.services.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository mockRepository;

    @InjectMocks
    private UserService mockService;

    @Test
    @DisplayName("Should save a new user")
    public void should_save_a_new_user() {
        User mockUser = new User();
        mockUser.setName("Dudu");
        mockUser.setEmail("dudu@email.com");

        when(mockRepository.save(mockUser)).thenReturn(mockUser);

        User result = mockService.create(mockUser);

        assertNotNull(result);
        assertEquals("Dudu", result.getName());
        assertEquals("dudu@email.com", result.getEmail());
        verify(mockRepository, times(1)).save(mockUser);
    }

    @Test
    @DisplayName("Should return a user list")
    public void should_return_a_user_list() {
        User mockUser = new User("6bbf3430-16e9-4c9a-b97b-e96f52050d43", "Dudu", "dudu@email.com", LocalDateTime.now(),
                LocalDateTime.now());

        List<User> mockUserList = new ArrayList<>();
        mockUserList.add(mockUser);

        when(mockRepository.findAll()).thenReturn(mockUserList);

        List<User> result = mockService.findAll();

        assertThat(result).hasSize(1);
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find a user by id and return the user")
    public void should_find_user_by_id() {
        String userId = "77b98863-21fe-4b78-a52c-f4df8b82db3e";
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setName("Dudu");
        mockUser.setEmail("dudu@email.com");

        when(mockRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User result = mockService.findById(userId);

        assertNotNull(result);
        assertEquals("Dudu", result.getName());
        assertEquals("dudu@email.com", result.getEmail());
        verify(mockRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should update a user")
    public void should_update_user() {
        String userId = "77b98863-21fe-4b78-a52c-f4df8b82db3e";
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setName("Dudu");
        mockUser.setEmail("dudu@email.com");

        User mockUserToUpdate = new User();
        mockUserToUpdate.setName("Updated name");
        mockUserToUpdate.setEmail("updated@email.com");

        when(mockRepository.save(mockUser)).thenReturn(mockUser);
        when(mockRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User result = mockService.update(userId, mockUserToUpdate);

        assertEquals(userId, result.getId());
        assertEquals("Updated name", result.getName());
        assertEquals("updated@email.com", result.getEmail());
        verify(mockRepository, times(1)).save(mockUser);
        verify(mockRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should delete a user by id")
    public void should_delete_user_by_id() {
        String userId = "77b98863-21fe-4b78-a52c-f4df8b82db3e";
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setName("Dudu");
        mockUser.setEmail("dudu@email.com");

        when(mockRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        doNothing().when(mockRepository).deleteById(userId);

        assertAll(() -> mockService.deleteById(userId));
        verify(mockRepository, times(1)).findById(userId);
        verify(mockRepository, times(1)).deleteById(userId);
    }
}
