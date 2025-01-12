package com.core_will_soft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AppUser {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String passwordHash;
    private boolean enabled;
}
