package com.ecommerce.middleware.pojo;

import javax.persistence.*;

@Entity(name = "user_credential")
public class UserCredential {
    @Id
    @GeneratedValue
    private int userCredentialId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String password;

    public int getUserCredentialId() {
        return userCredentialId;
    }

    public void setUserCredentialId(int userCredentialId) {
        this.userCredentialId = userCredentialId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
