package com.moxian.quartz.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import com.moxian.quartz.domain.InputVo.InputVoBuilder;
/**
 * 
* @Description: 计划任务信息
* @author snailxr
* @date 2014年6月6日 下午10:49:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleJob {

	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";
	private Long jobId;
	private Date createTime;
	private Date updateTime;
	private String jobName;
	private String jobGroup;
	private String jobStatus;
	private String cronExpression;
	private String description;
	private String beanClass;
	private String isConcurrent;
	private String springId;
	private String methodName;
	
}