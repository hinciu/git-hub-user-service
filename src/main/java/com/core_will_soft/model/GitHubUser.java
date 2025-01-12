package com.core_will_soft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "git_hub_user")
public class GitHubUser {
    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private int externalId;
    private String avatarUrl;
    private boolean deleted;

}
