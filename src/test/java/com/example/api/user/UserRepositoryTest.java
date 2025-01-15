package com.example.api.user;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.api.entities.User;
import com.example.api.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("Should save a new user")
    public void should_save_a_user() {
        User mockUser = new User();
        mockUser.setName("Dudu");
        mockUser.setEmail("dudu@email.com");

        User result = repository.save(mockUser);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(mockUser.getName(), result.getName());
        assertEquals(mockUser.getEmail(), result.getEmail());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    @DisplayName("Should return a user list")
    public void should_return_a_user_list() {
        User firstMockUser = new User();
        firstMockUser.setName("Dudu");
        firstMockUser.setEmail("dudu@email.com");

        User secondMockUser = new User();
        secondMockUser.setName("João");
        secondMockUser.setEmail("joao@email.com");

        repository.save(firstMockUser);
        repository.save(secondMockUser);

        List<User> result = repository.findAll();

        assertThat(result).hasSize(2);
        assertNotNull(result);
        assertEquals("Dudu", result.get(0).getName());
        assertEquals("João", result.get(1).getName());
    }

    @Test
    @DisplayName("Should return a user got by id")
    public void should_return_a_user_got_by_id() {
        User mockUser = new User();
        mockUser.setName("Dudu ");
        mockUser.setEmail("dudu@email.com");
        repository.save(mockUser);

        User result = repository.findById(mockUser.getId()).orElse(null);

        assertNotNull(result);
        assertEquals(mockUser, result);
    }

    @Test
    @DisplayName("Should update a user")
    public void should_update_a_user() {
        User mockUser = new User();
        mockUser.setName("Dudu ");
        mockUser.setEmail("dudu@email.com");
        repository.save(mockUser);

        mockUser.setName("Name updated");
        mockUser.setEmail("update@email.com");

        User result = repository.save(mockUser);

        assertNotNull(result);
        assertEquals("Name updated", result.getName());
        assertEquals("update@email.com", result.getEmail());
    }

    @Test
    @DisplayName("Should delete a user by id")
    public void should_delete_user_by_id() {
        User mockUser = new User();
        mockUser.setName("Dudu ");
        mockUser.setEmail("dudu@email.com");
        repository.save(mockUser);

        repository.deleteById(mockUser.getId());

        boolean result = repository.existsById(mockUser.getId());
        User resultNull = repository.findById(mockUser.getId()).orElse(null);

        assertEquals(0, repository.count());
        assertFalse(result);
        assertNull(resultNull);
    }
}
