package com.core_will_soft;

import com.core_will_soft.client.GitHubUserService;
import com.core_will_soft.mapper.GitHubUserMapper;
import com.core_will_soft.repository.GitHubUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@ConditionalOnProperty(name = "user.populate_on_startup", havingValue = "true", matchIfMissing = true)
public class ApplicationStartup {

    private final GitHubUserService gitHubUserService;
    private final GitHubUserRepository gitHubUserRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        if (gitHubUserRepository.count() == 0) {
            var users = gitHubUserService
                    .fetchUserList()
                    .stream()
                    .map(GitHubUserMapper::toGitHubUser)
                    .collect(Collectors.toList());
            gitHubUserRepository.saveAll(users);
        }

    }
}
