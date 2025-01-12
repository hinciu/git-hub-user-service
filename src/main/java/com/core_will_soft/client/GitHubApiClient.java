package com.core_will_soft.client;

import com.core_will_soft.dto.response.GitHubApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GitHubApiClient {

    private final RestTemplate restTemplate;

    @Value("${github.api.token}")
    private String githubToken;
    @Value("${github.api.user_list}")
    private String gitHubUserListApiUrl;
    @Value("${github.api.user_details}")
    private String gitHupUserInfoApiUrl;
    @Value("${github.api.users_per_page}")
    private int perPage;

    public GitHubApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<GitHubApiResponse[]> getUserList() {
        String url = UriComponentsBuilder.fromHttpUrl(gitHubUserListApiUrl)
                .queryParam("per_page", perPage)
                .toUriString();
        return exchange(url, GitHubApiResponse[].class);

    }

    public ResponseEntity<GitHubApiResponse[]> getNext(String url) {
        return exchange(url, GitHubApiResponse[].class);
    }

    public ResponseEntity<GitHubApiResponse> fetchUserInfo(int userId) {
        String url = gitHupUserInfoApiUrl + "/" + userId;
        return exchange(url, GitHubApiResponse.class);
    }

    private <T> ResponseEntity<T> exchange(String url, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
//        headers.set("Authorization", "Bearer " + githubToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
    }
}
