package com.bezkoder.springjwt.security.services;

import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.UserSummary;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    AuditLogService auditLogService;

    @Override
    public Boolean updateRole(UserSummary userSummary) {
        Optional<User> optionalUser = userRepository.findById(userSummary.getId());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Set<Role> newRoles = userSummary.getRoles().stream()
                    .map(roleName -> {
                        ERole roleEnum = ERole.valueOf(roleName);
                        return roleRepository.findByName(roleEnum)
                                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                    })
                    .collect(Collectors.toSet());

            user.setRoles(newRoles);
            userRepository.save(user);

            // 🔽 INSERT THIS BLOCK AFTER SAVING USER
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String performedBy = authentication.getName();

            auditLogService.logAction(
                    "UPDATE_ROLE",
                    performedBy,
                    user.getId(),
                    "Updated roles to: " + userSummary.getRoles().toString()
            );

            return true;
        }

        return false;
    }
}
