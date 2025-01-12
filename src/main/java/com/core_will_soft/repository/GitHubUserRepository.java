package com.core_will_soft.repository;

import com.core_will_soft.model.GitHubUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitHubUserRepository extends JpaRepository<GitHubUser, Long> {

    Page<GitHubUser> findAllByDeletedFalse(Pageable pageable);

    Long countBy();

}
