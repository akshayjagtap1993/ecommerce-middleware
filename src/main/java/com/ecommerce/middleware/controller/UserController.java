package com.ecommerce.middleware.controller;

import com.ecommerce.middleware.dto.RestApiResponse;
import com.ecommerce.middleware.pojo.User;
import com.ecommerce.middleware.pojo.UserCredential;
import com.ecommerce.middleware.repository.UserCredentialRepository;
import com.ecommerce.middleware.repository.UserRepository;
import com.ecommerce.middleware.security.PasswordEncryption;
import com.ecommerce.middleware.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @GetMapping(value = "/")
    public String hello() {
        return "Hello IntelliJ !!!";
    }

    @PostMapping(value = "/api/login")
    public RestApiResponse login(@RequestBody Map<String, Object> user) {
        User fetchedUser = iUserService.login(user);
        if(fetchedUser == null)
            return new RestApiResponse("failure", null, "User login failed");
        else
            return new RestApiResponse("success", fetchedUser, "User login successful");
    }

    @GetMapping(value = "/api/user")
    public RestApiResponse getAllUsers(){
        return new RestApiResponse ("success", userRepository.findAll(), "all users fetched");
    }

    @PostMapping(value = "/api/signup")
    public RestApiResponse signup(@RequestBody Map<String, Object> user){
        User registeredUser = iUserService.signup(user);
        return new RestApiResponse("success", registeredUser, "user registered successfully");
    }

//    @PostMapping(value = "/api/login")
//    public RestApiResponse login(@RequestBody Map<String, Object> request){
//        String username = (String) request.get("username");
//        String password = (String) request.get("password");
//        System.out.println("username -> " + username);
//
//        UserCredential fetchedUserCred = userCredentialRepository.findByEmailId(username);
//        String decryptedPassword = PasswordEncryption.decrypt(fetchedUserCred.getPassword());
//        if(fetchedUserCred == null || !password.equals(decryptedPassword)) {
//            return new RestApiResponse("failure", null, "Please check username or password");
//        }
//
////        String secretKey = "mySecretKey";
////        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
////                .commaSeparatedStringToAuthorityList("ROLE_USER");
////
////        String token = Jwts
////                .builder()
////                .setId("ecommerce")
////                .setSubject(username)
////                .claim("authorities",
////                        grantedAuthorities.stream()
////                                .map(GrantedAuthority::getAuthority)
////                                .collect(Collectors.toList()))
////                .setIssuedAt(new Date(System.currentTimeMillis()))
////                .setExpiration(new Date(System.currentTimeMillis() + 600000))
////                .signWith(SignatureAlgorithm.HS512,
////                        secretKey.getBytes()).compact();
////
////        token = "Bearer " + token;
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("username", username);
//        //response.put("token", token);
//        return new RestApiResponse("success", response, "Token generation successful");
//    }
}
