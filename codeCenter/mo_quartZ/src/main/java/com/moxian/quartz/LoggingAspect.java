/**
 * @Title: tt.java
 * @Package com.moxian.common.sso.aop
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月9日 下午5:06:46
 * @version Moxian M1 V1.0
 */
package com.moxian.quartz;

/**
 * @ClassName: tt
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月9日 下午5:06:46
 *
 */
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
 
@Slf4j
@Aspect
@Component
public class LoggingAspect {
   @Around("execution(* com.moxian.quartz.service.impl.*.*(..))")
   public Object beforeMethodService(ProceedingJoinPoint joinPoint) throws Throwable{
       String name = joinPoint.getSignature().getName();
       log.info("########## Call Method {} Begin ########## ", name);
       StopWatch sw = new StopWatch();
       try {
           sw.start();
           return joinPoint.proceed();
       } 
       finally{
           sw.stop();
           log.info("########## Call Method {} End ########## Method {} takes {}ms", name, name, sw.getTotalTimeMillis());
       }
   }
   
/*	@AfterThrowing(value="execution(* com.moxian.quartz.service.impl.*.*(..)) or execution(* com.moxian.quartz.service.impl.*.*())", throwing="throwable")
	public void serviceAfterThrowing(RuntimeException  throwable) {  
	   log.debug("##### Service 层异常捕获开始");
//       log.debug("产生异常的方法名称： "+method.getName());
//         
//       for(Object o:args){  
//           log.debug("方法的参数：   " + o.toString());
//       }  
//         
//       log.debug("代理对象：   " + target.getClass().getName());

       log.error("抛出的异常:    " + throwable.getMessage()+" >>>>>>> "  
               + throwable.getCause());
       log.error("异常详细信息：　　　" + throwable.fillInStackTrace()); 
       log.debug("##### Service 层异常捕获结束");
   } */
	
//	@AfterThrowing("execution(* com.moxian.quartz.dao.impl.*.*())")
//	public void daoAfterThrowing(Method method, Object[] args, Object target,  
//           RuntimeException  throwable) {  
//	   log.debug("##### Dao 层异常捕获开始");
//       log.debug("产生异常的方法名称： "+method.getName());
//         
//       for(Object o:args){  
//           log.debug("方法的参数：   " + o.toString());
//       }  
//         
//       log.debug("代理对象：   " + target.getClass().getName());
//
//       log.error("抛出的异常:    " + throwable.getMessage()+" >>>>>>> "  
//               + throwable.getCause());
//       log.error("异常详细信息：　　　" + throwable.fillInStackTrace()); 
//       log.debug("##### Dao 层异常捕获结束");
//   }  
}