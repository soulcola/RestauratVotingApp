package com.petrtitov.votingApp.web;

import lombok.Getter;
import org.springframework.lang.NonNull;
import com.petrtitov.votingApp.model.Role;
import com.petrtitov.votingApp.model.User;

public class AuthUser extends org.springframework.security.core.userdetails.User {

    @Getter
    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }

    public String email() {
        return user.getEmail();
    }

    public boolean hasRole(Role role) {
        return user.hasRole(role);
    }

    @Override
    public String toString() {
        return "AuthUser:" + id() + '[' + user.getEmail() + ']';
    }
}