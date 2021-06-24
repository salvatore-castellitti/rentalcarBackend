package com.rentalcar.rentalcarbackend.controller;

import com.rentalcar.rentalcarbackend.exception.ResourceNotFoundException;
import com.rentalcar.rentalcarbackend.jwt.JwtTokenProvider;
import com.rentalcar.rentalcarbackend.model.Role;
import com.rentalcar.rentalcarbackend.model.User;
import com.rentalcar.rentalcarbackend.repository.UserRepository;
import com.rentalcar.rentalcarbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/users/registration")
    public ResponseEntity<?> register(@RequestBody User user){
        if(userService.findByUsername(user.getUsername()) != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setRole(Role.CUSTOMER);
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/users/login")
    public ResponseEntity<?> getUser(Principal principal){
        if(principal == null){
            return ResponseEntity.ok(principal);
        }
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        User user = userService.findByUsername(authenticationToken.getName());
        user.setToken(jwtTokenProvider.generateToken(authenticationToken));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //get all employees
    @GetMapping("/users/list")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/users/add")
//    public User createUser(@RequestBody User user){
//        user.setRole(Role.CUSTOMER);
//        return  userService.saveUser(user);
//    }
    public ResponseEntity<?> createUser(@RequestBody User user){
        user.setRole(Role.CUSTOMER);
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }



    @PostMapping("/users/update")
    public User updateUser(@RequestBody User user){
        return  userService.updateUser(user);
    }

    //get user by id
    @GetMapping("/users/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not exist with id :" + id ));
        return ResponseEntity.ok(user);
    }
//
//    //delete user
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not exist with id :" + id ));

        userRepository.delete(user);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
//

}
