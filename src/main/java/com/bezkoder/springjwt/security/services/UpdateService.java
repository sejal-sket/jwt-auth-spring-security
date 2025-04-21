package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.payload.request.UserSummary;

public interface UpdateService {
    Boolean updateRole(UserSummary userSummary);
}
