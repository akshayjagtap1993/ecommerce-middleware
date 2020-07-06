//package com.ecommerce.middleware.security;
//
//import com.ecommerce.middleware.dto.RestApiResponse;
//import com.ecommerce.middleware.pojo.UserCredential;
//import com.ecommerce.middleware.repository.UserCredentialRepository;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.core.GrantedAuthority;
////import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@RestController
//public class LoginController {
//
//    @Autowired
//    private UserCredentialRepository userCredentialRepository;
//
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
//}
