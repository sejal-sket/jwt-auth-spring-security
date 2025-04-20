package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.payload.request.UserSummary;

import java.util.List;

public interface UserService {
    List<UserSummary> getUsersByRoles(List<String> rolesToFilter);
}

