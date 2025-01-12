package com.core_will_soft.dto;

import lombok.Data;

@Data
public class UserDetailsDTO {
    private String login;
    private Long id;
    private int externalId;
    private String avatarUrl;
}
