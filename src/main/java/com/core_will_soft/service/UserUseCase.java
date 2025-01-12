package com.core_will_soft.service;

import com.core_will_soft.dto.UserDetailsDTO;
import com.core_will_soft.dto.response.PageableResponse;
import org.springframework.data.domain.Pageable;

public interface UserUseCase {
    PageableResponse<UserDetailsDTO> getAllUsers(Pageable pageable);
}