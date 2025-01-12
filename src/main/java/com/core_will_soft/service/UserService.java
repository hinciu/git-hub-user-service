package com.core_will_soft.service;

import com.core_will_soft.dto.UserDetailsDTO;
import com.core_will_soft.dto.response.PageableResponse;
import com.core_will_soft.mapper.GitHubUserMapper;
import com.core_will_soft.repository.GitHubUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserUseCase {

    private final GitHubUserRepository gitHubUserRepository;

    @Override
    public PageableResponse<UserDetailsDTO> getAllUsers(Pageable pageable) {
        var page = gitHubUserRepository.findAll(pageable);
        var content = page.getContent()
                .stream()
                .map(GitHubUserMapper::userDetailsDTO)
                .collect(Collectors.toList());

        return PageableResponse.<UserDetailsDTO>builder()
                .data(content)
                .size(page.getSize())
                .offset(page.getPageable().getOffset())
                .totalCount(page.getTotalElements())
                .build();
    }
}
