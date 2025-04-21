package com.bezkoder.springjwt.security.services;


import com.bezkoder.springjwt.models.AuditLog;

import java.util.List;

public interface AuditLogService {
    void logAction(String action, String performedBy, Long targetUserId, String description);
    List<AuditLog> getAllLogs();
}
