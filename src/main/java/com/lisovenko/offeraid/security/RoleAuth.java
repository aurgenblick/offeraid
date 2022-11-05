package com.lisovenko.offeraid.security;

import org.springframework.security.core.GrantedAuthority;

public enum RoleAuth implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
