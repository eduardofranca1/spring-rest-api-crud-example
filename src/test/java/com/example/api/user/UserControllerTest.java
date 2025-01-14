package com.example.api.user;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.api.controllers.UserController;
import com.example.api.dto.UserRequestDto;
import com.example.api.entities.User;
import com.example.api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    public void UserController_CreateUser_ReturnUserCreated() throws Exception {
        given(userService.create(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userRequest.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userRequest.getEmail()));
    }

    @Test
    public void UserController_GetUsers() throws Exception {
        List<User> userListResponse = Collections.singletonList(userResponse);

        when(userService.findAll()).thenReturn(userListResponse);

        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/user").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(userListResponse.size())));
    }

    @Test
    public void UserController_GetUserById() throws Exception {
        when(userService.findById(userId)).thenReturn(userResponse);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/findById/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userResponse)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(userResponse.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(userResponse.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userResponse.getEmail())));
    }

    @Test
    public void UserController_UpdateUser() throws Exception {

        User userUpdatedResponse = new User();
        userUpdatedResponse.setId(userId);
        userUpdatedResponse.setName("Eduardo");
        userUpdatedResponse.setEmail("eduardo@email.com");

        when(userService.update(eq(userId), any(User.class))).thenReturn(userUpdatedResponse);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userUpdatedResponse.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userUpdatedResponse.getEmail()));
    }

    // @Test
    // public void Old_UserController_UpdateUser() throws Exception {

    // User userUpdatedResponse = new User();
    // userUpdatedResponse.setId(userId);
    // userUpdatedResponse.setName("Eduardo");
    // userUpdatedResponse.setEmail("eduardo@email.com");

    // when(userService.update(userId,
    // userRequest)).thenReturn(userUpdatedResponse);

    // ResultActions response =
    // mockMvc.perform(MockMvcRequestBuilders.put("/api/user/" + userId)
    // .contentType(MediaType.APPLICATION_JSON)
    // .content(objectMapper.writeValueAsString(userRequest)));

    // response.andExpect(MockMvcResultMatchers.status().isOk())
    // .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userUpdatedResponse.getName()))
    // .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userUpdatedResponse.getEmail()));
    // }

    @Test
    public void UserController_DeleteUserById() throws Exception {

        doNothing().when(userService).deleteById(userId);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
