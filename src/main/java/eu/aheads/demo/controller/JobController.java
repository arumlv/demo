package eu.aheads.demo.controller;

import eu.aheads.demo.batch.JobInfo;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    private ApplicationContext context;

    private final Map<String, String> urls = new TreeMap(Map.of(
            "beneficialJob", "http://dati.ur.gov.lv/beneficial_owners/beneficial_owners.csv",
            "registerJob", "http://dati.ur.gov.lv/register/register.csv"));

    private final Log logger = LogFactory.getLog(JobController.class);

    @GetMapping("/api/jobs")
    public Set<String> getJobs() throws Exception {
        return urls.keySet();
    }

    @GetMapping("/api/jobs/{name}")
    public JobInfo getJobInfo(@PathVariable("name") String name) throws Exception {

        JobInfo jobInfo = new JobInfo();
        JobInstance lastJobInstance = jobExplorer.getLastJobInstance(name);
        jobInfo.setName(name);

        if (lastJobInstance != null) {
            JobExecution lastJobExecution = jobExplorer.getLastJobExecution(lastJobInstance);
            if (lastJobExecution != null) {
                jobInfo.setStartTime(lastJobExecution.getStartTime());
                jobInfo.setEndTime(lastJobExecution.getEndTime());
                jobInfo.setStatus(lastJobExecution.getStatus().name());
            }
        }
        Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(name);
        runningJobExecutions.forEach(runningJobExecution -> {
            runningJobExecution.getStepExecutions().forEach(stepExecution -> {
                jobInfo.setWriteCount(stepExecution.getWriteCount());
            });
        });
        logger.debug(jobInfo);
        return jobInfo;
    }

    @PostMapping("/api/jobs/{name}")
    public void startJob(@PathVariable("name") String name) throws Exception {

        logger.info("start job " + name);

        Map<String, JobParameter> params = Map.of(
                "time", new JobParameter(Calendar.getInstance().getTimeInMillis()),
                "input", new JobParameter(urls.get(name)));
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Job job = context.getBean(name, Job.class);
                    jobLauncher.run(job, new JobParameters(params));
                } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException
                        | JobInstanceAlreadyCompleteException | JobRestartException e) {
                    logger.error(e.toString());
                }
            }
        };
        thread.start();
    }
}
