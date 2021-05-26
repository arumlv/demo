package eu.aheads.demo.config;

import java.io.File;
import java.util.Calendar;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobConfigurationTest {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    private ApplicationContext context;

    @Test
    public void testRegisterJob() throws Exception {

        File file = new File("src/test/resources/register.csv");
        Map<String, JobParameter> params = Map.of(
                "time", new JobParameter(Calendar.getInstance().getTimeInMillis()),
                "input", new JobParameter(file.toURI().toURL().toString()));
        Job job = context.getBean("registerJob", Job.class);
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters(params));
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
        assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
    }

    @Test
    public void testBeneficialJob() throws Exception {

        File file = new File("src/test/resources/beneficial_owners.csv");
        Map<String, JobParameter> params = Map.of(
                "time", new JobParameter(Calendar.getInstance().getTimeInMillis()),
                "input", new JobParameter(file.toURI().toURL().toString()));
        Job job = context.getBean("beneficialJob", Job.class);
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters(params));
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
        assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
    }
}
