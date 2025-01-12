package com.core_will_soft.processing;


import com.core_will_soft.model.GitHubUser;
import com.core_will_soft.repository.GitHubUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
@Slf4j
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final GitHubUserRepository repository;
    private final UserItemProcessor userItemProcessor;
    private final DataSource dataSource;
    private JdbcTemplate jdbcTemplate;


    @Bean
    public Job updateGitHubUserJob(Step fetchAndUpdateStep) {
        return new JobBuilder("updateGitHubUserJob", jobRepository)
                .start(fetchAndUpdateStep)
                .listener(listener())
                .build();
    }

    @Bean
    public Step fetchAndUpdateStep(RepositoryItemReader<GitHubUser> reader,
                                   ItemWriter<GitHubUser> writer) {
        return new StepBuilder("processItemStep", jobRepository)
                .<GitHubUser, GitHubUser>chunk(100, transactionManager)
                .reader(reader)
                .processor(userItemProcessor)
                .writer(writer)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public RepositoryItemReader<GitHubUser> reader() {
        RepositoryItemReader<GitHubUser> reader = new RepositoryItemReader<>();
        reader.setRepository(repository);
        reader.setMethodName("findAllByDeletedFalse");
        reader.setSort(Map.of("id", Sort.Direction.ASC));
        reader.setPageSize(100);
        return reader;
    }

    @Bean
    public ItemWriter<GitHubUser> writer() {
        return repository::saveAll;
    }

    @Bean
    JobExecutionListener listener() {
        return new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("Starting job: {}", jobExecution.getJobInstance().getJobName());
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                log.info("Job {} completed with status: {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
            }
        };
    }

    @PostConstruct
    public void initializeBatchSchema() {
        String checkTableQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'batch_job_instance'";
        Integer count = jdbcTemplate.queryForObject(checkTableQuery, Integer.class);

        if (count == 0) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new org.springframework.core.io.ClassPathResource("org/springframework/batch/core/schema-postgresql.sql"));
            populator.execute(dataSource);
            log.info("Spring Batch schema initialized.");
        } else {
            log.info("Spring Batch schema already exists. Skipping initialization.");
        }
    }
}