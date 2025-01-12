package com.core_will_soft.dto.response;

import lombok.Data;

@Data
public class GitHubApiResponse {
    private String login;
    private int id;
    private String avatar_url;
}
