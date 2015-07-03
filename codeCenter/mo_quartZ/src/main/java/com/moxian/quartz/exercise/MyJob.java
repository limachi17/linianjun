package com.moxian.quartz.exercise;

import lombok.extern.slf4j.Slf4j;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/*
 * 1,非紧急的调度 ,使用这个job处理
 * 2,紧急调度,时间不能出现任何偏差的数据库调度,放入另外一个job,使用thrift调度进行处理.
 */
@Slf4j
public class MyJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
//		context.getJobRunTime();
		log.info("kafka 往服务器发消息  ,kafka 客户端,收到消息,立即执行调度");

	}

}
