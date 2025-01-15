package com.example.api.user;

import org.hamcrest.CoreMatchers;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.api.controllers.UserController;
import com.example.api.entities.User;
import com.example.api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

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
                userRequest = new User.Builder().name("Dudu").email("dudu@email.com").build();
                userResponse = new User.Builder().id(userId).name("Dudu").email("dudu@email.com").build();
        }

        @Test
        @DisplayName("Should save a new user")
        public void should_save_a_new_user() throws Exception {
                given(userService.create(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

                api.perform(post("/api/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRequest)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.name").value(userRequest.getName()))
                                .andExpect(jsonPath("$.email").value(userRequest.getEmail()));
        }

        @Test
        @DisplayName("Should return a user list")
        public void should_return_a_user_list() throws Exception {
                List<User> userListResponse = Collections.singletonList(userResponse);

                when(userService.findAll()).thenReturn(userListResponse);

                api.perform(get("/api/user")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$.size()",
                                                CoreMatchers.is(userListResponse.size())));
        }

        @Test
        @DisplayName("Should return a user got by id")
        public void should_return_a_user_got_by_id() throws Exception {
                when(userService.findById(userId)).thenReturn(userResponse);

                api.perform(get("/api/user/findById/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userResponse))).andExpect(status().isOk())
                                .andExpect(jsonPath("$.id",
                                                CoreMatchers.is(userResponse.getId())))
                                .andExpect(jsonPath("$.name",
                                                CoreMatchers.is(userResponse.getName())))
                                .andExpect(jsonPath("$.email",
                                                CoreMatchers.is(userResponse.getEmail())));
        }

        @Test
        @DisplayName("Should update a user")
        public void should_update_a_user() throws Exception {

                User userUpdatedResponse = new User();
                userUpdatedResponse.setId(userId);
                userUpdatedResponse.setName("Eduardo");
                userUpdatedResponse.setEmail("eduardo@email.com");

                when(userService.update(eq(userId), any(User.class))).thenReturn(userUpdatedResponse);

                api.perform(put("/api/user/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRequest))).andExpect(status().isOk())
                                .andExpect(jsonPath("$.name")
                                                .value(userUpdatedResponse.getName()))
                                .andExpect(jsonPath("$.email")
                                                .value(userUpdatedResponse.getEmail()));
        }

        @Test
        @DisplayName("Should delete a user by id")
        public void should_delete_a_user_by_id() throws Exception {
                api.perform(delete("/api/user/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
        }
}
