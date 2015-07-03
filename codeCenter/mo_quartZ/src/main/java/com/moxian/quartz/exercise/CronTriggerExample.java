package com.moxian.quartz.exercise;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.impl.StdSchedulerFactory;

@Slf4j
public class CronTriggerExample {
	
	public void run() throws Exception {
		log.info("------- Initializing -------------------");

		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		
//		QuartzData
		for(QuartzData qd: InitializingQuartzData.getQdList()){
			JobDetail job = newJob(SimpleJob.class).withIdentity(qd.getJobname(), qd.getJobGroupName())
					.build();
			CronTrigger trigger = newTrigger().withIdentity(qd.getTriggerName(), qd.getTriggerGroupName())
					.withSchedule(cronSchedule(qd.getCronScheduleExcepession())).build();
			Date ft = sched.scheduleJob(job, trigger);
			log.info(job.getKey() + " has been scheduled to run at: " + ft
					+ " and repeat based on expression: "
					+ trigger.getCronExpression());
		}
		
		sched.start();
		int i=0;
		while(true){
			try {
				// wait five minutes to show jobs
				Thread.sleep(300L * 1000L);
				// executing...
			} catch (Exception e) {
				//
			}
			if(++i > 6)
				break;
		}
		
		sched.shutdown(true);



		

		log.info("------- Started Scheduler -----------------");

		log.info("------- Waiting five minutes... ------------");
		

		log.info("------- Shutting Down ---------------------");

		sched.shutdown(true);

		log.info("------- Shutdown Complete -----------------");

		SchedulerMetaData metaData = sched.getMetaData();
		log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");

	}

	public static void main(String[] args) throws Exception {

		CronTriggerExample example = new CronTriggerExample();
		example.run();
	}
	
//	JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1")
//	.build();

//job = newJob(SimpleJob.class).withIdentity("job2", "group1").build();
//
//trigger = newTrigger().withIdentity("trigger2", "group1")
//	.withSchedule(cronSchedule("15 0/2 * * * ?")).build();
//
//ft = sched.scheduleJob(job, trigger);
//log.info(job.getKey() + " has been scheduled to run at: " + ft
//	+ " and repeat based on expression: "
//	+ trigger.getCronExpression());
//
//job = newJob(SimpleJob.class).withIdentity("job3", "group1").build();
//
//trigger = newTrigger().withIdentity("trigger3", "group1")
//	.withSchedule(cronSchedule("0 0/2 8-17 * * ?")).build();
//
//ft = sched.scheduleJob(job, trigger);
//log.info(job.getKey() + " has been scheduled to run at: " + ft
//	+ " and repeat based on expression: "
//	+ trigger.getCronExpression());
//
//job = newJob(SimpleJob.class).withIdentity("job4", "group1").build();
//
//trigger = newTrigger().withIdentity("trigger4", "group1")
//	.withSchedule(cronSchedule("0 0/3 17-23 * * ?")).build();
//
//ft = sched.scheduleJob(job, trigger);
//log.info(job.getKey() + " has been scheduled to run at: " + ft
//	+ " and repeat based on expression: "
//	+ trigger.getCronExpression());
//
//job = newJob(SimpleJob.class).withIdentity("job5", "group1").build();
//
//trigger = newTrigger().withIdentity("trigger5", "group1")
//	.withSchedule(cronSchedule("0 0 10am 1,15 * ?")).build();
//
//ft = sched.scheduleJob(job, trigger);
//log.info(job.getKey() + " has been scheduled to run at: " + ft
//	+ " and repeat based on expression: "
//	+ trigger.getCronExpression());
//
//job = newJob(SimpleJob.class).withIdentity("job6", "group1").build();
//
//trigger = newTrigger().withIdentity("trigger6", "group1")
//	.withSchedule(cronSchedule("0,30 * * ? * MON-FRI")).build();
//
//ft = sched.scheduleJob(job, trigger);
//log.info(job.getKey() + " has been scheduled to run at: " + ft
//	+ " and repeat based on expression: "
//	+ trigger.getCronExpression());
//
//job = newJob(SimpleJob.class).withIdentity("job7", "group1").build();
//
//trigger = newTrigger().withIdentity("trigger7", "group1")
//	.withSchedule(cronSchedule("0,30 * * ? * SAT,SUN")).build();
//
//ft = sched.scheduleJob(job, trigger);
//log.info(job.getKey() + " has been scheduled to run at: " + ft
//	+ " and repeat based on expression: "
//	+ trigger.getCronExpression());

}
