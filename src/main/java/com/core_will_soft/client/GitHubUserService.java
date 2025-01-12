package com.core_will_soft.client;

import com.core_will_soft.dto.error.ErrorCode;
import com.core_will_soft.dto.response.GitHubApiResponse;
import com.core_will_soft.exception.ConnectionException;
import com.core_will_soft.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
public class GitHubUserService {
    private static final Pattern NEXT_LINK_PATTERN = Pattern.compile("<([^>]+)>;\\s*rel=\"next\"");
    @Autowired
    private GitHubApiClient gitHubApiClient;
    @Value("${user.amount}")
    private int userAmount;
    @Value("${github.api.users_per_page}")
    private int perPage;

    public List<GitHubApiResponse> fetchUserList() {
        List<GitHubApiResponse> result = new ArrayList<>();
        try {
            int loops = (userAmount / perPage) - 1;
            var firstChunk = gitHubApiClient.getUserList();
            if (firstChunk.getBody() == null) {
                return result;
            }
            result.addAll(List.of(firstChunk.getBody()));
            var next = firstChunk.getHeaders().get("Link");
            if (next == null || next.isEmpty()) {
                return result;
            }

            for (int i = 1; i <= loops; i++) {
                var matcher = NEXT_LINK_PATTERN.matcher(next.get(0));
                if (!matcher.find()) {
                    break;
                }
                var response = gitHubApiClient.getNext(matcher.group(1));
                Optional.ofNullable(response.getBody()).ifPresent(v -> result.addAll(List.of(v)));
                next = response.getHeaders().get("Link");
                if (next == null || next.isEmpty()) {
                    break;
                }
            }

        } catch (RestClientException e) {
            log.error("Failed to connect to GitHub API", e);
            return result;
        }
        return result;
    }

    public GitHubApiResponse fetchUserInfo(int userId) {
        try {
            return gitHubApiClient.fetchUserInfo(userId).getBody();
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("User with id {} not found", userId);
                throw new ResourceNotFoundException(ErrorCode.RESOURCE_NOT_FOUND, userId, ex.getMessage());
            }
            throw ex;

        } catch (RestClientException e) {
            log.error("Failed to connect to GitHub API", e);
            throw new ConnectionException(ErrorCode.FAILED_TO_CONNECT.getMessage());
        }
    }
}
