package com.core_will_soft;

import com.core_will_soft.client.GitHubApiClient;
import com.core_will_soft.dto.response.GitHubApiResponse;
import com.core_will_soft.repository.GitHubUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

public class ProcessingIT extends BaseIT {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job updateGitHubUserJob;

    @Autowired
    private GitHubUserRepository repository;

    @MockBean
    private GitHubApiClient gitHubApiClient;

    @Test
    @Sql("classpath:sql/insertUser.sql")
    @Sql(scripts = "classpath:sql/clearTables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    void testBatchJobExecution() throws Exception {

        var apiResponse = new GitHubApiResponse();
        apiResponse.setAvatar_url("updatedUrl");
        apiResponse.setLogin("updatedLogin");
        apiResponse.setId(10);


        when(gitHubApiClient.fetchUserInfo(anyInt()))
                .thenReturn(ResponseEntity.ok().body(apiResponse));

        JobExecution jobExecution = jobLauncher.run(updateGitHubUserJob,
                new JobParametersBuilder().addLong("timestamp", System.currentTimeMillis())
                        .toJobParameters());

        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        var updatedUser = repository.findById(1L);
        assertTrue(updatedUser.isPresent());
        assertEquals("updatedUrl", updatedUser.get().getAvatarUrl());
        assertEquals("updatedLogin", updatedUser.get().getLogin());
        assertEquals(10, updatedUser.get().getExternalId());
    }
}
