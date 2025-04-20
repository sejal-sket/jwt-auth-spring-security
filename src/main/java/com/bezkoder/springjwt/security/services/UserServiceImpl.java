package com.bezkoder.springjwt.security.services;


import java.util.List;
import java.util.stream.Collectors;

import com.bezkoder.springjwt.payload.request.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserSummary> getUsersByRoles(List<String> rolesToFilter) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> rolesToFilter.contains(role.getName().name().toLowerCase())))
                .map(this::mapToUserSummary)
                .collect(Collectors.toList());
    }

    private UserSummary mapToUserSummary(User user) {
        return new UserSummary(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toList())
        );
    }
}
