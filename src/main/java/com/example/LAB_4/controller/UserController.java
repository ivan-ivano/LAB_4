package com.example.LAB_4.controller;

import com.example.LAB_4.dto.UserDTO;
import com.example.LAB_4.entity.user.User;
import com.example.LAB_4.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepo userRepo;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return userRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @PostMapping()
    public void addUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getPhoneNumber());
        userRepo.save(user);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam long id) {
        userRepo.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable long id, @RequestBody UserDTO userDTO) {
        User user = userRepo.findById(id).orElseThrow();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userRepo.save(user);
    }
}
