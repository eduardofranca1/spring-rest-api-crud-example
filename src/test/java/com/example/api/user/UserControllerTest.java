package com.example.api.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.api.controllers.UserController;
import com.example.api.entities.User;
import com.example.api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

        @Autowired
        private MockMvc api;

        @MockBean
        private UserService userService;

        @Autowired
        private ObjectMapper objectMapper;
        private User userRequest;
        private User userResponse;

        private String userId;

        @BeforeEach
        public void init() {
                userId = "77b98863-21fe-4b78-a52c-f4df8b82db3e";
                userRequest = new User();
                userRequest.setName("Dudu");
                userRequest.setEmail("dudu@email.com");

                userResponse = new User();
                userResponse.setId(userId);
                userResponse.setName("Dudu");
                userResponse.setEmail("dudu@email.com");
        }

        @Test
        @DisplayName("Should save a new user")
        public void should_save_a_new_user() throws Exception {
                given(userService.create(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

                api.perform(post("/api/user")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRequest)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(APPLICATION_JSON))
                                .andExpect(jsonPath("$.name").value(userRequest.getName()))
                                .andExpect(jsonPath("$.email").value(userRequest.getEmail()));
        }

        @Test
        @DisplayName("Should return a user list")
        public void should_return_a_user_list() throws Exception {
                List<User> userListResponse = Collections.singletonList(userResponse);

                when(userService.findAll()).thenReturn(userListResponse);

                api.perform(get("/api/user")
                                .contentType(APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$.size()").value(userListResponse.size()));
        }

        @Test
        @DisplayName("Should return a user got by id")
        public void should_return_a_user_got_by_id() throws Exception {
                when(userService.findById(userId)).thenReturn(userResponse);

                api.perform(get("/api/user/findById/" + userId)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userResponse))).andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(userResponse.getId()))
                                .andExpect(jsonPath("$.name").value(userResponse.getName()))
                                .andExpect(jsonPath("$.email").value(userResponse.getEmail()));

        }

        @Test
        @DisplayName("Should update a user")
        public void should_update_a_user() throws Exception {

                User updatedUserResponse = new User();
                updatedUserResponse.setId(userId);
                updatedUserResponse.setName("Eduardo");
                updatedUserResponse.setEmail("eduardo@email.com");

                when(userService.update(eq(userId), any(User.class))).thenReturn(updatedUserResponse);

                api.perform(put("/api/user/" + userId)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRequest))).andExpect(status().isOk())
                                .andExpect(jsonPath("$.name")
                                                .value(updatedUserResponse.getName()))
                                .andExpect(jsonPath("$.email")
                                                .value(updatedUserResponse.getEmail()));
        }

        @Test
        @DisplayName("Should delete a user by id")
        public void should_delete_a_user_by_id() throws Exception {
                api.perform(delete("/api/user/" + userId)
                                .contentType(APPLICATION_JSON)).andExpect(status().isNoContent());
        }
}
