package com.example.demo.batchprocessing;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class BatchScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importUserJob;

    @Scheduled(cron = "*/30 * * * * ?") // 30초마다 실행
    public void runBatchJob() throws Exception {
        jobLauncher.run(importUserJob,
                new JobParametersBuilder()
                        .addDate("runDate", new Date())
                        .toJobParameters());
    }
}