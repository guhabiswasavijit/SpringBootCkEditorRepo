package self.demo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("listener")
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger("JobLogger");
    @Autowired
    private  JdbcTemplate jdbcTemplate;
    @Override
    public void afterJob(JobExecution jobExecution) {
        String articles = jobExecution.getExecutionContext().getString("articleInserted");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("!!! JOB FINISHED! Time to verify the results");

            String query = "INSERT INTO JobRun(articleInserted) VALUES(?)";
            jdbcTemplate.update(query,articles);
        }
    }
}
