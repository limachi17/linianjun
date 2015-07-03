package com.moxian.quartz;

import lombok.extern.slf4j.Slf4j;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.moxian.quartz.exercise.MyJob;
import com.moxian.quartz.util.SpringUtil;
import com.moxian.quartz.vo.PersonAge;

@Slf4j
@Configuration
public class QuartzConfig {
	@Value("${webClient.connectionPool.maxTotal}")
	protected int webClientConnectionPoolMaxTotal;

	@Bean
	SpringUtil springUtil() {
		return new SpringUtil();
	}

	@Bean
	PersonAge personAge() {
		PersonAge ap = new PersonAge();
		ap.setAge(webClientConnectionPoolMaxTotal);
		return ap;
	}
	
	@Bean
	MyJob myJob(){
		return new MyJob();
	}

	@Bean
	Scheduler scheduler() {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched=null;
		try {
			sched = sf.getScheduler();
		} catch (SchedulerException e) {
			log.warn(e.getMessage(),e);
		}
		return sched;
	}

}
