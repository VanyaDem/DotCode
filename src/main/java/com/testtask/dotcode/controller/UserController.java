package com.testtask.dotcode.controller;

import com.testtask.dotcode.domain.entity.User;
import com.testtask.dotcode.dto.UserDto;
import com.testtask.dotcode.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.testtask.dotcode.utils.DtoMapper.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(@RequestParam int page, @RequestParam int size) {
        var pageOfUsers = userService.findAll(PageRequest.of(page, size));
        var userDtoList = mapToUserDtoList(pageOfUsers.getContent());
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Pages", Integer.toString(pageOfUsers.getTotalPages()))
                .body(userDtoList);
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
