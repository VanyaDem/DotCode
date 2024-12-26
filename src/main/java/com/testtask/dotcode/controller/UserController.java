package com.testtask.dotcode.controller;

import com.testtask.dotcode.domain.entity.User;
import com.testtask.dotcode.dto.UserDto;
import com.testtask.dotcode.service.UserService;
import com.testtask.dotcode.utils.DtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.testtask.dotcode.utils.DtoMapper.mapToUser;
import static com.testtask.dotcode.utils.DtoMapper.mapToUserDto;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        var users = userService.findAll();
        var userDtoList = DtoMapper.toUserDtoList(users);
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") Long id) {
        var user = userService.findById(id);
        var userDto = mapToUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
        User user = userService.save(mapToUser(userDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(getUri(user))
                .body(mapToUserDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("id") Long id) {
        User user = userService.update(id, mapToUser(userDto));
        return ResponseEntity.ok(mapToUserDto(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    private URI getUri(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
    }
}
