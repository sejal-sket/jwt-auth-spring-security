package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.UserSummary;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Boolean updateRole(UserSummary userSummary) {
        Optional<User> optionalUser = userRepository.findByUsername(userSummary.getUsername());

        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Error: User not found");
        }

        User user = optionalUser.get();
        Set<Role> newRoles = new HashSet<>();

        for (String roleStr : userSummary.getRoles()) {
            ERole roleEnum;
            switch (roleStr.toLowerCase()) {
                case "admin":
                    roleEnum = ERole.ROLE_ADMIN;
                    break;
                case "mod":
                case "moderator":
                    roleEnum = ERole.ROLE_MODERATOR;
                    break;
                case "user":
                    roleEnum = ERole.ROLE_USER;
                    break;
                default:
                    throw new RuntimeException("Error: Role '" + roleStr + "' is invalid.");
            }

            Role role = roleRepository.findByName(roleEnum)
                    .orElseThrow(() -> new RuntimeException("Error: Role '" + roleEnum + "' not found in DB."));

            newRoles.add(role);
        }

        user.setRoles(newRoles);
        userRepository.save(user);

        return true;
    }
}
