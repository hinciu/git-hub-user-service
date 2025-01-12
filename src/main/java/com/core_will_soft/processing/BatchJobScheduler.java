package com.core_will_soft.processing;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "batch.scheduling.enabled", havingValue = "true", matchIfMissing = true)
public class BatchJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job updateGitHubUserJob;

    @Scheduled(fixedRateString = "${batch.scheduling.interval}", timeUnit = TimeUnit.MINUTES)
    public void runUpdateJob() {
        try {
            jobLauncher.run(updateGitHubUserJob, new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters());
        } catch (Exception e) {
            log.error("Error occurred while running the job", e);
        }
    }
}