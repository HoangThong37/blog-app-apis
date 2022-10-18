package com.codewithme.blog.controllers;

import com.codewithme.blog.payloads.UserDTO;
import com.codewithme.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

      @Autowired
      private UserService userService;

     // POST- create user
     @PostMapping("/")
     public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
         UserDTO createUser = userService.createUser(userDTO);
         return new ResponseEntity<>(createUser, HttpStatus.CREATED);
     }

     // PUT- update user
     @PutMapping("/{userId}")
     public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("userId") Integer uId) {
         UserDTO updateUser = userService.updateUser(userDTO, uId);
         return ResponseEntity.ok(updateUser);
     }

     // DELETE- delete user
     @DeleteMapping("/{userId}")
     public void deleteUser(@PathVariable("userId") Integer userId) {
         if (userId != null) {
             userService.deleteUser(userId);
         }
         // return ResponseEntity(Map.Of("MESSAGE", "USER DELETED SUCCESSFULLY"), HttpStatus.OK);
     }

     // GET- get user by id
     @GetMapping("/{userId}")
     public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Integer userId) {
         UserDTO getUserById = userService.getUserById(userId);
         return ResponseEntity.ok(getUserById);
     }

    // GET- get user by id
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
        //return new ResponseEntity<>(createUser, HttpStatus);
    }
}
