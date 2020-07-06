package com.ecommerce.middleware.service;

import com.ecommerce.middleware.pojo.User;

import java.util.Map;

public interface IUserService {

    public User login(Map<String, Object> user);

    public User signup(Map<String, Object> user);
}
