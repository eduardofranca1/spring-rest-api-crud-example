package com.example.api.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.api.entities.User;
import com.example.api.repositories.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("It should create a user")
    public void testCreateUser() {
        User mockUser = new User.Builder().name("Dudu").email("dudu@email.com").build();
        User user = this.repository.save(mockUser);
        assertEquals(mockUser, user);
        assertNotNull(user);
    }

    @Test
    @DisplayName("It should return a user list")
    public void testReturnUserList() {
        User firstMockUser = new User.Builder().name("Dudu").email("dudu@email.com").build();
        User secondMockUser = new User.Builder().name("João").email("joao@email.com").build();

        this.repository.save(firstMockUser);
        this.repository.save(secondMockUser);

        List<User> result = this.repository.findAll();

        assertThat(result).hasSize(2);
        assertNotNull(result);
        assertEquals("Dudu", result.get(0).getName());
    }

    @Test
    @DisplayName("It should return a user got by id")
    public void testReturnUserGotById() {
        User mockUser = new User.Builder().name("Dudu").email("dudu@email.com").build();
        this.repository.save(mockUser);
        User result = this.repository.findById(mockUser.getId()).orElse(null);
        assertNotNull(result);
        assertEquals(mockUser, result);
    }

    @Test
    @DisplayName("It should update a user")
    public void testUpdateUser() {
        User mockUser = new User.Builder().name("Dudu").email("dudu@email.com").build();
        this.repository.save(mockUser);

        mockUser.setName("Name updated");
        mockUser.setEmail("update@email.com");

        User result = this.repository.save(mockUser);

        assertNotNull(result);
        assertEquals("Name updated", result.getName());
        assertEquals("update@email.com", result.getEmail());
    }

    @Test
    @DisplayName("It should delete a user")
    public void testDeleteUser() {
        User mockUser = new User.Builder().name("Dudu").email("dudu@email.com").build();
        this.repository.save(mockUser);

        this.repository.deleteById(mockUser.getId());

        boolean result = this.repository.existsById(mockUser.getId());
        User resultNull = this.repository.findById(mockUser.getId()).orElse(null);

        assertEquals(0, this.repository.count());
        assertFalse(result);
        assertNull(resultNull);
    }
}
