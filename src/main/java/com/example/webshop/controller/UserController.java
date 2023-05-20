package com.example.webshop.controller;

import com.example.webshop.model.dto.UserDto;
import com.example.webshop.model.mapper.UserMapper;
import com.example.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @GetMapping("/page")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<List<UserDto>> getUsersPage(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersPage(page,size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }

    @PostMapping
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(userDto));
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/find")
    @PreAuthorize("@authService.hasAccess({'ADMINISTRATOR'})")
    public ResponseEntity<UserDto> findUserByEmail(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(userService.findUserByEmail(email)));

    }

}
