package com.planner.workers.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER"),
    GUEST("ROLE_GUEST");

    UserRole(String value){
        this.value = value;
    }

    private String value;
}
