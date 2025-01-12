package com.core_will_soft.processing;

import com.core_will_soft.client.GitHubUserService;
import com.core_will_soft.dto.response.GitHubApiResponse;
import com.core_will_soft.exception.ResourceNotFoundException;
import com.core_will_soft.model.GitHubUser;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class UserItemProcessor implements ItemProcessor<GitHubUser, GitHubUser> {


    private final GitHubUserService gitHubRepoService;


    public UserItemProcessor(
            GitHubUserService gitHubRepoService) {
        this.gitHubRepoService = gitHubRepoService;
    }


    @Override
    public GitHubUser process(GitHubUser item) throws Exception {
        try {
            var response = gitHubRepoService.fetchUserInfo(item.getExternalId());
            if (hasChanges(item, response)) {
                item.setExternalId(response.getId());
                item.setLogin(response.getLogin());
                item.setAvatarUrl(response.getAvatar_url());
            }

        } catch (ResourceNotFoundException exception) {
            item.setDeleted(true);
        } catch (HttpClientErrorException exception) {
            return null;
        }
        return item;
    }


    public boolean hasChanges(GitHubUser gitHubUser, GitHubApiResponse apiResponse) {
        if (gitHubUser.getExternalId() != apiResponse.getId()) {
            return true;
        }
        if (!gitHubUser.getLogin().equals(apiResponse.getLogin())) {
            return true;
        }
        return !gitHubUser.getAvatarUrl().equals(apiResponse.getAvatar_url());
    }
}
