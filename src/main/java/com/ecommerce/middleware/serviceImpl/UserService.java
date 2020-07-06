package com.ecommerce.middleware.serviceImpl;

import com.ecommerce.middleware.pojo.User;
import com.ecommerce.middleware.pojo.UserCredential;
import com.ecommerce.middleware.repository.UserCredentialRepository;
import com.ecommerce.middleware.repository.UserRepository;
import com.ecommerce.middleware.security.PasswordEncryption;
import com.ecommerce.middleware.service.IUserService;
import com.ecommerce.middleware.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Map;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCredentialRepository userCredentialRepository;

    @Override
    public User login(Map<String, Object> user) {
        String username = (String) user.get("emailId");
        String password = (String) user.get("password");
        UserCredential userCred = userCredentialRepository.findByEmailId(username);
        if(userCred == null || !password.equals(PasswordEncryption.decrypt(userCred.getPassword()))){
            return null;
        }
        return userCred.getUser();
    }

    @Override
    @Transactional
    public User signup(Map<String, Object> user) {

        User dbUser = new User((String)user.get("fullName"), Utils.strToDate((String)user.get("dateOfBirth")),
                (String)user.get("emailId"), false);
        dbUser = userRepository.save(dbUser);

        UserCredential userCred = new UserCredential();
        userCred.setUser(dbUser);
        userCred.setPassword(PasswordEncryption.encrypt((String)user.get("password")));
        userCredentialRepository.save(userCred);
        return dbUser;
    }
}
