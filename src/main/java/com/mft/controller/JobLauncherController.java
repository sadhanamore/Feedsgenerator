package com.mft.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
@RestController
public class JobLauncherController {

	@Autowired
	JobLauncher jobLauncher;
 
	@Autowired
	Job job;
 
	@RequestMapping("/launchjob")
	public String handle()throws Exception {
 
		
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {

	}	
		return "Done";
	}
}

