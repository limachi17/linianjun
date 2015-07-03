package com.moxian.quartz;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

 

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class ScheduledProcessor {
	
	private final AtomicInteger counter = new AtomicInteger();
 
    @Scheduled(cron = "0/1 * * * * ?")
    public void process() {
        System.out.println("processing next 10 at " + new Date());
      
    }

}
