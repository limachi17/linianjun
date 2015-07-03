package com.moxian.quartz.exercise;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import org.quartz.DateBuilder;

public class InitializingQuartzData {
	private static QuartzData[] qdlist = new QuartzData[10];
	

	static {
//		Map<Class, QuartzData> myMap = new HashMap<Class, QuartzData>();
		initQuartzData(InitializingQuartzData.qdlist);
		
//		for(QuartzData qd : qdlist)
//			myMap.put(MyJob.class, qd);
	}

	private static void initQuartzData(QuartzData[] qd) {
		Random rd = new Random();
		for (int i = 0; i < qd.length; i++) {
			String jobname="job"+i;
			String jobGroupName="jobGroup"+i;
			String triggerName="trigger"+i;
			String triggerGroupName="triggerGroup"+i;
			String cronScheduleExcepession=""+rd.nextInt(60)+" 0/"+rd.nextInt(20)+" * * * ?";//0 0/30 9-17 * * ? 
			Date startTime=DateBuilder.nextGivenSecondDate(null, 15);
		}
	}
	
	public static QuartzData[] getQdList(){
		return InitializingQuartzData.qdlist;
	}
}


