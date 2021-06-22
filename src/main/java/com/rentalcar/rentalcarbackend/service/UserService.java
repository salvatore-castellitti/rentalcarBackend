package com.rentalcar.rentalcarbackend.service;

import com.rentalcar.rentalcarbackend.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    User findByUsername(String username);

    List<User> findAllUser();

    User updateUser(User user);
}
