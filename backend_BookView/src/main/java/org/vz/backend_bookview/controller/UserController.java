package org.vz.backend_bookview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vz.backend_bookview.model.Users;
import org.vz.backend_bookview.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Users> register(@RequestBody Users user){
        return userService.addUser(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUsersById(@PathVariable int id){
        return userService.getUsersById(id);
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<String> updateUsers(@PathVariable int id , @RequestBody Users user){
        return userService.updateUsers(id,user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable int id){
        return userService.deleteUsers(id);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return userService.verify(user);
    }

}
