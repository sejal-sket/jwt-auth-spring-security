package com.bezkoder.springjwt.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    private String performedBy;

    private Long targetUserId;

    private String description;

    private LocalDateTime timestamp;

    public void setPerformedBy(String performedBy) {  this.performedBy = performedBy;
    }

    public void setAction(String action) { this.action = action;
    }

    public void setTargetUserId(Long targetUserId) { this.targetUserId=targetUserId;
    }

    public void setDescription(String description) { this.description = description;
    }

    public void setTimestamp(LocalDateTime now) { this.timestamp = now;
    }

    // Getters and Setters
}
