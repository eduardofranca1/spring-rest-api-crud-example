package com.example.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.api.dto.UserMapper;
import com.example.api.dto.UserRequestDto;
import com.example.api.dto.UserResponseDto;
import com.example.api.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    @Autowired
    UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto create(@RequestBody UserRequestDto userRequestDto) {
        return UserMapper
                .transformEntityToResponseDto(this.service.create(UserMapper.transformDtoIntoEntity(userRequestDto)));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> findAll() {
        return UserMapper.transformEntityToListResponseDto(this.service.findAll());
    }

    @GetMapping("/findById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto findById(@PathVariable String id) {
        return UserMapper.transformEntityToResponseDto(this.service.findById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto update(@PathVariable String id, @RequestBody UserRequestDto userRequestDto) {
        return UserMapper.transformEntityToResponseDto(
                this.service.update(id, UserMapper.transformDtoIntoEntity(userRequestDto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        this.service.deleteById(id);
    }

}
