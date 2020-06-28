package com.ecommerce.middleware.repository;

import com.ecommerce.middleware.pojo.UserCredential;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends CrudRepository<UserCredential, Integer> {

    @Query("select uc from com.ecommerce.middleware.pojo.UserCredential uc join uc.user u where u.emailId=?1")
    public UserCredential findByEmailId(String emailId);
}
