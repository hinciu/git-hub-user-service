package com.core_will_soft.controller;


import com.core_will_soft.dto.UserDetailsDTO;
import com.core_will_soft.dto.response.PageableResponse;
import com.core_will_soft.service.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.core_will_soft.controller.UserController.BASE_URL;

@RestController
@RequestMapping(path = BASE_URL)
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class UserController {

    public static final String BASE_URL = "/api/v1/users";


    private final UserUseCase gitHubUserService;


    @GetMapping
    public PageableResponse<UserDetailsDTO> getAll(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return gitHubUserService.getAllUsers(pageable);
    }
}
