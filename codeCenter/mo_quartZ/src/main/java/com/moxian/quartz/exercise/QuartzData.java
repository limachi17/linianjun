package com.moxian.quartz.exercise;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class QuartzData {
	private String jobname;
	private String jobGroupName;
	private String triggerName;
	private String triggerGroupName;
	private String cronScheduleExcepession;
	private Date startTime;

}