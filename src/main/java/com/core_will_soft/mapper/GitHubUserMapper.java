package com.core_will_soft.mapper;

import com.core_will_soft.dto.UserDetailsDTO;
import com.core_will_soft.dto.response.GitHubApiResponse;
import com.core_will_soft.model.GitHubUser;

public class GitHubUserMapper {
    public static UserDetailsDTO userDetailsDTO(GitHubUser gitHubUser) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setLogin(gitHubUser.getLogin());
        userDetailsDTO.setId(gitHubUser.getId());
        userDetailsDTO.setExternalId(gitHubUser.getExternalId());
        userDetailsDTO.setAvatarUrl(gitHubUser.getAvatarUrl());
        return userDetailsDTO;
    }

    public static GitHubUser toGitHubUser(GitHubApiResponse gitHubApiResponse) {
        GitHubUser gitHubUser = new GitHubUser();
        gitHubUser.setLogin(gitHubApiResponse.getLogin());
        gitHubUser.setExternalId(gitHubApiResponse.getId());
        gitHubUser.setAvatarUrl(gitHubApiResponse.getAvatar_url());
        return gitHubUser;
    }
}
