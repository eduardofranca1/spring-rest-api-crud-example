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
    @DisplayName("Should save a new user")
    public void should_save_a_user() {
        User mockUser = new User.Builder().name("Dudu").email("dudu@email.com").build();
        User user = repository.save(mockUser);
        assertEquals(mockUser, user);
        assertNotNull(user);
    }

    @Test
    @DisplayName("Should return a user list")
    public void should_return_a_user_list() {
        User firstMockUser = new User.Builder().id("6bbf3430-16e9-4c9a-b97b-e96f52050d43").name("Dudu")
                .email("dudu@email.com").build();
        User secondMockUser = new User.Builder().id("75f0f71b-ec13-40f9-a5b9-56306d2b2b3c").name("João")
                .email("joao@email.com").build();

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
        User mockUser = new User.Builder().name("Dudu").email("dudu@email.com").build();
        repository.save(mockUser);
        User result = repository.findById(mockUser.getId()).orElse(null);
        assertNotNull(result);
        assertEquals(mockUser, result);
    }

    @Test
    @DisplayName("Should update a user")
    public void should_update_a_user() {
        User mockUser = new User.Builder().name("Dudu").email("dudu@email.com").build();
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
        User mockUser = new User.Builder().name("Dudu").email("dudu@email.com").build();
        repository.save(mockUser);

        repository.deleteById(mockUser.getId());

        boolean result = repository.existsById(mockUser.getId());
        User resultNull = repository.findById(mockUser.getId()).orElse(null);

        assertEquals(0, repository.count());
        assertFalse(result);
        assertNull(resultNull);
    }
}
