package self.demo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import self.demo.model.MindWave;
import self.demo.solr.beans.Restaurant;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


@Configuration
@EnableBatchProcessing
@EnableScheduling
public class UploadJob {
    private final Logger logger = LoggerFactory.getLogger(UploadJob.class);
    private AtomicBoolean enabled = new AtomicBoolean(true);
    private AtomicInteger batchRunCounter = new AtomicInteger(0);
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ArticleReader reader;
    @Autowired
    private ArticleProcessor processor;
    @Autowired
    private ArticleWriter articleWriter;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobCompletionNotificationListener listener;

    @Scheduled(fixedRate = 2000)
    public void launchJob() throws Exception {
        Date date = new Date();
        logger.debug("scheduler starts at " + date);
        if (enabled.get()) {
            JobExecution jobExecution = jobLauncher.run(solrIndexUpload(listener,step1()), new JobParametersBuilder().addDate("launchDate", date)
                    .toJobParameters());
            batchRunCounter.incrementAndGet();
            logger.debug("Batch job ends with status as " + jobExecution.getStatus());
        }
        logger.debug("scheduler ends ");
    }
    public void stop() {
        enabled.set(false);
    }

    public void start() {
        enabled.set(true);
    }
    @Bean
    public Job solrIndexUpload(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("solrExportJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<MindWave,Restaurant> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(articleWriter)
                .build();
    }
}
