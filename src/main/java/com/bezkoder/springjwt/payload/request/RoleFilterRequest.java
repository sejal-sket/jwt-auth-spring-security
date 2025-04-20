package com.bezkoder.springjwt.payload.request;

import java.util.List;
import java.util.Set;


public class RoleFilterRequest {
    private List<String> roles;

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}