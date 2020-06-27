package com.ecommerce.middleware.controller;

import com.ecommerce.middleware.dto.RestApiResponse;
import com.ecommerce.middleware.pojo.User;
import com.ecommerce.middleware.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/")
    public String hello() {
        return "Hello IntelliJ !!!";
    }

    @GetMapping(value = "/user")
    public RestApiResponse getAllUsers(){
        return new RestApiResponse ("success", userRepository.findAll(), "all users fetched");
    }
}
